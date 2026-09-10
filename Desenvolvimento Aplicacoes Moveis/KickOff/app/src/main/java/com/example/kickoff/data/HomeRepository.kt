package com.example.kickoff.data

import android.util.Log
import com.example.kickoff.model.HomeNextMatchUi
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale

class HomeRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun loadNextMatch(
        onResult: (HomeNextMatchUi?, String?) -> Unit
    ) {
        val uid = auth.currentUser?.uid

        if (uid == null) {
            onResult(null, "Utilizador não autenticado")
            return
        }

        db.collection("users")
            .document(uid)
            .collection("leagues")
            .get()
            .addOnSuccessListener { userLeaguesSnapshot ->

                val leagueIds = userLeaguesSnapshot.documents.map { it.id }

                if (leagueIds.isEmpty()) {
                    onResult(null, null)
                    return@addOnSuccessListener
                }

                val allMatches = mutableListOf<HomeNextMatchUi>()
                var completed = 0

                leagueIds.forEach { leagueId ->

                    val leagueRef = db.collection("leagues").document(leagueId)

                    leagueRef.get()
                        .addOnSuccessListener { leagueDoc ->

                            val leagueName = leagueDoc.getString("name") ?: "League"

                            leagueRef.collection("matches")
                                .whereEqualTo("status", "scheduled")
                                .get()
                                .addOnSuccessListener { matchesSnapshot ->

                                    matchesSnapshot.documents.forEach { matchDoc ->

                                        val scheduledAt = matchDoc.getTimestamp("scheduledAt")
                                            ?: return@forEach

                                        val millis = scheduledAt.toDate().time
                                        val now = System.currentTimeMillis()

                                        if (millis > now) {
                                            allMatches.add(
                                                HomeNextMatchUi(
                                                    leagueId = leagueId,
                                                    matchId = matchDoc.id,
                                                    leagueName = leagueName,
                                                    teamAName = matchDoc.getString("teamAName") ?: "Team A",
                                                    teamBName = matchDoc.getString("teamBName") ?: "Team B",
                                                    dateText = formatDate(scheduledAt),
                                                    timeText = formatTime(scheduledAt),
                                                    location = matchDoc.getString("location") ?: "",
                                                    scheduledMillis = millis
                                                )
                                            )
                                        }
                                        Log.d("HomeRepository", "League IDs: $leagueIds")
                                        Log.d("HomeRepository", "League: $leagueName")
                                        Log.d("HomeRepository", "Match: ${matchDoc.id}")
                                        Log.d("HomeRepository", "Status: ${matchDoc.getString("status")}")
                                        Log.d("HomeRepository", "ScheduledAt: ${matchDoc.getTimestamp("scheduledAt")}")
                                        Log.d("HomeRepository", "Millis: $millis")
                                        Log.d("HomeRepository", "Now: $now")
                                        Log.d("HomeRepository", "Is future: ${millis > now}")
                                    }

                                    completed++

                                    if (completed == leagueIds.size) {
                                        val nextMatch = allMatches.minByOrNull { it.scheduledMillis }
                                        onResult(nextMatch, null)
                                    }
                                }
                                .addOnFailureListener { error ->
                                    onResult(null, error.message)
                                }
                        }
                        .addOnFailureListener { error ->
                            onResult(null, error.message)
                        }
                }
            }
            .addOnFailureListener { error ->
                onResult(null, error.message)
            }
    }

    private fun formatDate(timestamp: Timestamp?): String {
        if (timestamp == null) return ""
        val formatter = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.ENGLISH)
        return formatter.format(timestamp.toDate())
    }

    private fun formatTime(timestamp: Timestamp?): String {
        if (timestamp == null) return ""
        val formatter = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        return formatter.format(timestamp.toDate())
    }
}