package com.example.kickoff.data

import com.example.kickoff.model.MatchPlayerReportUi
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class MatchReportRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun loadMatchReport(
        leagueId: String,
        matchId: String,
        onResult: (
            teamAName: String,
            teamBName: String,
            players: List<MatchPlayerReportUi>,
            error: String?
        ) -> Unit
    ) {
        val leagueRef = db.collection("leagues").document(leagueId)
        val matchRef = leagueRef.collection("matches").document(matchId)

        matchRef.get()
            .addOnSuccessListener { matchDoc ->
                val teamAName = matchDoc.getString("teamAName") ?: "Team A"
                val teamBName = matchDoc.getString("teamBName") ?: "Team B"

                leagueRef.collection("fantasyPlayers")
                    .get()
                    .addOnSuccessListener { playersSnapshot ->
                        val players = playersSnapshot.documents.map { doc ->
                            MatchPlayerReportUi(
                                playerId = doc.id,
                                name = doc.getString("name") ?: ""
                            )
                        }.sortedBy { it.name }

                        onResult(teamAName, teamBName, players, null)
                    }
                    .addOnFailureListener { error ->
                        onResult("", "", emptyList(), error.message)
                    }
            }
            .addOnFailureListener { error ->
                onResult("", "", emptyList(), error.message)
            }
    }

    fun submitMatchReport(
        leagueId: String,
        matchId: String,
        teamAScore: Int,
        teamBScore: Int,
        players: List<MatchPlayerReportUi>,
        onResult: (Boolean, String?) -> Unit
    ) {
        val uid = auth.currentUser?.uid

        if (uid == null) {
            onResult(false, "Utilizador não autenticado")
            return
        }

        val leagueRef = db.collection("leagues").document(leagueId)
        val matchRef = leagueRef.collection("matches").document(matchId)

        leagueRef.get()
            .addOnSuccessListener { leagueDoc ->
                val creatorId = leagueDoc.getString("creatorId")

                if (creatorId != uid) {
                    onResult(false, "Não tens permissão para submeter este relatório")
                    return@addOnSuccessListener
                }

                val playedPlayers = players.filter { it.played }

                val playerPoints = playedPlayers.associate {
                    it.playerId to calculatePoints(it)
                }

                db.runBatch { batch ->

                    batch.update(
                        matchRef,
                        mapOf(
                            "status" to "finished",
                            "teamAScore" to teamAScore,
                            "teamBScore" to teamBScore,
                            "finishedAt" to Timestamp.now()
                        )
                    )

                    batch.update(
                        leagueRef,
                        mapOf(
                            "currentWeek" to FieldValue.increment(1)
                        )
                    )

                    playedPlayers.forEach { player ->
                        val pointsEarned = playerPoints[player.playerId] ?: 0

                        val statsRef = matchRef
                            .collection("playerStats")
                            .document(player.playerId)

                        batch.set(
                            statsRef,
                            mapOf(
                                "playerId" to player.playerId,
                                "team" to player.team,
                                "played" to true,
                                "goals" to player.goals,
                                "assists" to player.assists,
                                "ownGoals" to player.ownGoals,
                                "missedPenalties" to player.missedPenalties,
                                "savedPenalties" to player.savedPenalties,
                                "saves" to player.saves,
                                "goalsConceded" to player.goalsConceded,
                                "pointsEarned" to pointsEarned
                            )
                        )

                        val fantasyPlayerRef = leagueRef
                            .collection("fantasyPlayers")
                            .document(player.playerId)

                        batch.set(
                            fantasyPlayerRef,
                            mapOf(
                                "played" to FieldValue.increment(1),
                                "goals" to FieldValue.increment(player.goals.toLong()),
                                "assists" to FieldValue.increment(player.assists.toLong()),
                                "ownGoals" to FieldValue.increment(player.ownGoals.toLong()),
                                "missedPenalties" to FieldValue.increment(player.missedPenalties.toLong()),
                                "savedPenalties" to FieldValue.increment(player.savedPenalties.toLong()),
                                "saves" to FieldValue.increment(player.saves.toLong()),
                                "goalsConceded" to FieldValue.increment(player.goalsConceded.toLong()),
                                "points" to FieldValue.increment(pointsEarned.toLong())
                            ),
                            SetOptions.merge()
                        )
                    }

                }.addOnSuccessListener {
                    updateFantasyTeamsAndMembers(
                        leagueId = leagueId,
                        matchId = matchId,
                        playerPoints = playerPoints,
                        onResult = onResult
                    )
                }.addOnFailureListener { error ->
                    onResult(false, error.message)
                }
            }
            .addOnFailureListener { error ->
                onResult(false, error.message)
            }
    }

    private fun updateFantasyTeamsAndMembers(
        leagueId: String,
        matchId: String,
        playerPoints: Map<String, Int>,
        onResult: (Boolean, String?) -> Unit
    ) {
        val leagueRef = db.collection("leagues").document(leagueId)
        val matchRef = leagueRef.collection("matches").document(matchId)

        leagueRef.collection("fantasyTeams")
            .get()
            .addOnSuccessListener { teamsSnapshot ->

                db.runBatch { batch ->

                    teamsSnapshot.documents.forEach { teamDoc ->
                        val userId = teamDoc.id

                        val startingSeven =
                            teamDoc.get("startingSeven") as? List<String> ?: emptyList()

                        val weekPoints = startingSeven.sumOf { playerId ->
                            playerPoints[playerId] ?: 0
                        }

                        val teamRef = leagueRef
                            .collection("fantasyTeams")
                            .document(userId)

                        batch.set(
                            teamRef,
                            mapOf(
                                "totalPoints" to FieldValue.increment(weekPoints.toLong()),
                                "transfersLeft" to 2,
                                "lastMatchId" to matchId,
                                "updatedAt" to Timestamp.now()
                            ),
                            SetOptions.merge()
                        )

                        val memberRef = leagueRef
                            .collection("members")
                            .document(userId)

                        batch.set(
                            memberRef,
                            mapOf(
                                "points" to FieldValue.increment(weekPoints.toLong())
                            ),
                            SetOptions.merge()
                        )

                        val userRef = db.collection("users").document(userId)

                        batch.set(
                            userRef,
                            mapOf(
                                "globalPoints" to FieldValue.increment(weekPoints.toLong()),
                                "matchesPlayed" to FieldValue.increment(1)
                            ),
                            SetOptions.merge()
                        )

                        val userResultRef = matchRef
                            .collection("userResults")
                            .document(userId)

                        batch.set(
                            userResultRef,
                            mapOf(
                                "userId" to userId,
                                "points" to weekPoints,
                                "createdAt" to Timestamp.now()
                            ),
                            SetOptions.merge()
                        )
                    }

                }.addOnSuccessListener {
                    onResult(true, null)
                }.addOnFailureListener { error ->
                    onResult(false, error.message)
                }
            }
            .addOnFailureListener { error ->
                onResult(false, error.message)
            }
    }

    private fun calculatePoints(player: MatchPlayerReportUi): Int {
        var points = 0

        if (player.played) points += 1

        points += player.goals * 5
        points += player.assists * 3
        points += player.saves
        points += player.savedPenalties * 5

        points -= player.ownGoals * 2
        points -= player.missedPenalties * 3
        points -= player.goalsConceded

        return points
    }
}