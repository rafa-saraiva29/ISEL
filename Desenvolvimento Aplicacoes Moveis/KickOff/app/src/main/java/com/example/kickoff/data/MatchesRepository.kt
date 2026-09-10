package com.example.kickoff.data

import com.example.kickoff.model.Match
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MatchesRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun getMatches(
        leagueId: String,
        onResult: (
            isCreator: Boolean,
            nextMatch: Match?,
            upcomingMatches: List<Match>,
            pastMatches: List<Match>,
            error: String?
        ) -> Unit
    ) {
        val uid = auth.currentUser?.uid

        if (uid == null) {
            onResult(false, null, emptyList(), emptyList(), "Utilizador não autenticado")
            return
        }

        val leagueRef = db.collection("leagues").document(leagueId)

        leagueRef.get()
            .addOnSuccessListener { leagueDoc ->
                val creatorId = leagueDoc.getString("creatorId") ?: ""
                val isCreator = creatorId == uid

                leagueRef.collection("matches")
                    .get()
                    .addOnSuccessListener { snapshot ->

                        val matchDocs = snapshot.documents

                        val userResultTasks = matchDocs.map { matchDoc ->
                            leagueRef
                                .collection("matches")
                                .document(matchDoc.id)
                                .collection("userResults")
                                .document(uid)
                                .get()
                        }

                        Tasks.whenAllSuccess<com.google.firebase.firestore.DocumentSnapshot>(
                            userResultTasks
                        ).addOnSuccessListener { userResults ->

                            val userPointsByMatchId = userResults.associate { resultDoc ->
                                val matchId = resultDoc.reference.parent.parent?.id ?: ""
                                val points = resultDoc.getLong("points")?.toInt() ?: 0
                                matchId to points
                            }

                            val matches = matchDocs.map { doc ->
                                val scheduledAt = doc.getTimestamp("scheduledAt")
                                val millis = scheduledAt?.toDate()?.time ?: 0L
                                val status = doc.getString("status") ?: "scheduled"

                                Match(
                                    matchId = doc.id,
                                    week = doc.getLong("week")?.toInt() ?: 1,
                                    dateText = formatDate(scheduledAt),
                                    timeText = formatTime(scheduledAt),
                                    teamAName = doc.getString("teamAName") ?: "Team A",
                                    teamBName = doc.getString("teamBName") ?: "Team B",
                                    teamAScore = doc.getLong("teamAScore")?.toInt(),
                                    teamBScore = doc.getLong("teamBScore")?.toInt(),
                                    status = status,
                                    scheduledMillis = millis,
                                    userPoints = userPointsByMatchId[doc.id] ?: 0
                                )
                            }

                            val scheduledMatches = matches
                                .filter { it.status == "scheduled" }
                                .sortedBy { it.scheduledMillis }

                            val nextMatch = scheduledMatches.firstOrNull()

                            val upcomingMatches = scheduledMatches.drop(1)

                            val pastMatches = matches
                                .filter { it.status == "finished" }
                                .sortedByDescending { it.scheduledMillis }

                            onResult(
                                isCreator,
                                nextMatch,
                                upcomingMatches,
                                pastMatches,
                                null
                            )

                        }.addOnFailureListener { error ->
                            onResult(
                                isCreator,
                                null,
                                emptyList(),
                                emptyList(),
                                error.message
                            )
                        }
                    }
                    .addOnFailureListener { error ->
                        onResult(
                            isCreator,
                            null,
                            emptyList(),
                            emptyList(),
                            error.message
                        )
                    }
            }
            .addOnFailureListener { error ->
                onResult(
                    false,
                    null,
                    emptyList(),
                    emptyList(),
                    error.message
                )
            }
    }

    fun createMatch(
        leagueId: String,
        week: Int,
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        minute: Int,
        teamAName: String,
        teamBName: String,
        location: String,
        notes: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        val uid = auth.currentUser?.uid

        if (uid == null) {
            onResult(false, "Utilizador não autenticado")
            return
        }

        val leagueRef = db.collection("leagues").document(leagueId)

        leagueRef.get()
            .addOnSuccessListener { leagueDoc ->
                val creatorId = leagueDoc.getString("creatorId")

                if (creatorId != uid) {
                    onResult(false, "Não tens permissão para criar jogos")
                    return@addOnSuccessListener
                }

                val calendar = Calendar.getInstance().apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, day)
                    set(Calendar.HOUR_OF_DAY, hour)
                    set(Calendar.MINUTE, minute)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }

                val matchRef = leagueRef.collection("matches").document()

                val matchData = hashMapOf(
                    "matchId" to matchRef.id,
                    "week" to week,
                    "scheduledAt" to Timestamp(calendar.time),
                    "teamAName" to teamAName,
                    "teamBName" to teamBName,
                    "location" to location,
                    "notes" to notes,
                    "status" to "scheduled",
                    "teamAScore" to null,
                    "teamBScore" to null,
                    "createdAt" to Timestamp.now(),
                    "startedAt" to null,
                    "finishedAt" to null
                )

                matchRef.set(matchData)
                    .addOnSuccessListener {
                        onResult(true, null)
                    }
                    .addOnFailureListener { error ->
                        onResult(false, error.message)
                    }
            }
            .addOnFailureListener { error ->
                onResult(false, error.message)
            }
    }

    fun getLeagueCurrentWeek(
        leagueId: String,
        onResult: (Int, String?) -> Unit
    ) {
        db.collection("leagues")
            .document(leagueId)
            .get()
            .addOnSuccessListener { doc ->
                onResult(doc.getLong("currentWeek")?.toInt() ?: 1, null)
            }
            .addOnFailureListener { error ->
                onResult(1, error.message)
            }
    }

    private fun formatDate(timestamp: Timestamp?): String {
        if (timestamp == null) return ""
        val formatter = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.ENGLISH)
        return formatter.format(timestamp.toDate())
    }

    private fun formatTime(timestamp: Timestamp?): String {
        if (timestamp == null) return ""
        val formatter = SimpleDateFormat("EEE, dd MMM, HH:mm", Locale.ENGLISH)
        return formatter.format(timestamp.toDate()).uppercase()
    }
}