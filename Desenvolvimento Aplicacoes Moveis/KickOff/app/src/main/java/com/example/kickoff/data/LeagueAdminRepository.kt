package com.example.kickoff.data

import com.example.kickoff.model.FantasyPlayer
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LeagueAdminRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun addFantasyPlayer(
        leagueId: String,
        name: String,
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
                    onResult(false, "Não tens permissão para administrar esta liga")
                    return@addOnSuccessListener
                }

                val playerRef = leagueRef
                    .collection("fantasyPlayers")
                    .document()

                val playerData = hashMapOf(
                    "playerId" to playerRef.id,
                    "name" to name,

                    "played" to 0,
                    "goals" to 0,
                    "assists" to 0,
                    "ownGoals" to 0,
                    "missedPenalties" to 0,
                    "savedPenalties" to 0,
                    "saves" to 0,
                    "goalsConceded" to 0,

                    "points" to 0,
                    "createdAt" to Timestamp.now()
                )

                playerRef.set(playerData)
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

    fun getFantasyPlayers(
        leagueId: String,
        onResult: (List<FantasyPlayer>, String?) -> Unit
    ) {
        db.collection("leagues")
            .document(leagueId)
            .collection("fantasyPlayers")
            .get()
            .addOnSuccessListener { snapshot ->
                val players = snapshot.documents.map { doc ->
                    FantasyPlayer(
                        playerId = doc.getString("playerId") ?: doc.id,
                        name = doc.getString("name") ?: "",
                        played = doc.getLong("played")?.toInt() ?: 0,
                        goals = doc.getLong("goals")?.toInt() ?: 0,
                        assists = doc.getLong("assists")?.toInt() ?: 0,
                        ownGoals = doc.getLong("ownGoals")?.toInt() ?: 0,
                        missedPenalties = doc.getLong("missedPenalties")?.toInt() ?: 0,
                        savedPenalties = doc.getLong("savedPenalties")?.toInt() ?: 0,
                        saves = doc.getLong("saves")?.toInt() ?: 0,
                        goalsConceded = doc.getLong("goalsConceded")?.toInt() ?: 0,
                        points = doc.getLong("points")?.toInt() ?: 0
                    )
                }

                onResult(players, null)
            }
            .addOnFailureListener { error ->
                onResult(emptyList(), error.message)
            }
    }
}