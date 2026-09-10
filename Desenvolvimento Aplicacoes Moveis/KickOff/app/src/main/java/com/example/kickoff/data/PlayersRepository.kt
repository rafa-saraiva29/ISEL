package com.example.kickoff.data

import com.example.kickoff.model.FantasyPlayer
import com.google.firebase.firestore.FirebaseFirestore

class PlayersRepository {

    private val db = FirebaseFirestore.getInstance()

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
                        points = doc.getLong("points")?.toInt() ?: 0,
                        played = doc.getLong("played")?.toInt() ?: 0,
                        goals = doc.getLong("goals")?.toInt() ?: 0,
                        assists = doc.getLong("assists")?.toInt() ?: 0,
                        ownGoals = doc.getLong("ownGoals")?.toInt() ?: 0,
                        missedPenalties = doc.getLong("missedPenalties")?.toInt() ?: 0,
                        savedPenalties = doc.getLong("savedPenalties")?.toInt() ?: 0,
                        saves = doc.getLong("saves")?.toInt() ?: 0,
                        goalsConceded = doc.getLong("goalsConceded")?.toInt() ?: 0
                    )
                }.sortedByDescending { it.points }

                onResult(players, null)
            }
            .addOnFailureListener { error ->
                onResult(emptyList(), error.message)
            }
    }
}