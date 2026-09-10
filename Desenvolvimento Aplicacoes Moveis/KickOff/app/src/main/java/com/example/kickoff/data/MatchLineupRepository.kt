package com.example.kickoff.data

import com.example.kickoff.model.MatchLineupPlayerUi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MatchLineupRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun loadMatchLineup(
        leagueId: String,
        matchId: String,
        onResult: (List<MatchLineupPlayerUi>, Int, String?) -> Unit
    ) {
        val uid = auth.currentUser?.uid

        if (uid == null) {
            onResult(emptyList(), 0, "Utilizador não autenticado")
            return
        }

        val leagueRef = db.collection("leagues").document(leagueId)

        leagueRef.collection("fantasyTeams")
            .document(uid)
            .get()
            .addOnSuccessListener { teamDoc ->

                val startingSeven =
                    teamDoc.get("startingSeven") as? List<String> ?: emptyList()

                if (startingSeven.isEmpty()) {
                    onResult(emptyList(), 0, "Ainda não tens uma fantasy team guardada")
                    return@addOnSuccessListener
                }

                leagueRef.collection("fantasyPlayers")
                    .get()
                    .addOnSuccessListener { playersSnapshot ->

                        val playerNames = playersSnapshot.documents.associate {
                            it.id to (it.getString("name") ?: "Unknown")
                        }

                        leagueRef.collection("matches")
                            .document(matchId)
                            .collection("playerStats")
                            .get()
                            .addOnSuccessListener { statsSnapshot ->

                                val pointsByPlayer = statsSnapshot.documents.associate {
                                    it.id to (it.getLong("pointsEarned")?.toInt() ?: 0)
                                }

                                val players = startingSeven.map { playerId ->
                                    MatchLineupPlayerUi(
                                        playerId = playerId,
                                        name = playerNames[playerId] ?: "Unknown",
                                        pointsEarned = pointsByPlayer[playerId] ?: 0
                                    )
                                }

                                val total = players.sumOf { it.pointsEarned }

                                onResult(players, total, null)
                            }
                            .addOnFailureListener { error ->
                                onResult(emptyList(), 0, error.message)
                            }
                    }
                    .addOnFailureListener { error ->
                        onResult(emptyList(), 0, error.message)
                    }
            }
            .addOnFailureListener { error ->
                onResult(emptyList(), 0, error.message)
            }
    }
}