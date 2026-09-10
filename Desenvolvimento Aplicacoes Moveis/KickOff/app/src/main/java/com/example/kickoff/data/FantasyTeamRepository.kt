package com.example.kickoff.data

import com.example.kickoff.model.FantasyPlayerUi
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FantasyTeamRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun loadFantasyTeamData(
        leagueId: String,
        onResult: (
            players: List<FantasyPlayerUi>,
            startingIds: List<String>,
            transfersLeft: Int,
            userLeaguePoints: Int,
            error: String?
        ) -> Unit
    ) {
        val uid = auth.currentUser?.uid

        if (uid == null) {
            onResult(emptyList(), emptyList(), 2, 0, "Utilizador não autenticado")
            return
        }

        val leagueRef = db.collection("leagues").document(leagueId)

        leagueRef.collection("fantasyPlayers")
            .get()
            .addOnSuccessListener { playersSnapshot ->

                val players = playersSnapshot.documents.map { doc ->
                    FantasyPlayerUi(
                        playerId = doc.getString("playerId") ?: doc.id,
                        name = doc.getString("name") ?: "",
                        points = doc.getLong("points")?.toInt() ?: 0
                    )
                }

                leagueRef.collection("fantasyTeams")
                    .document(uid)
                    .get()
                    .addOnSuccessListener { teamDoc ->

                        val startingIds =
                            teamDoc.get("startingSeven") as? List<String> ?: emptyList()

                        val transfersLeft =
                            teamDoc.getLong("transfersLeft")?.toInt() ?: 2

                        leagueRef.collection("members")
                            .document(uid)
                            .get()
                            .addOnSuccessListener { memberDoc ->

                                val userLeaguePoints =
                                    memberDoc.getLong("points")?.toInt() ?: 0

                                onResult(
                                    players,
                                    startingIds,
                                    transfersLeft,
                                    userLeaguePoints,
                                    null
                                )
                            }
                            .addOnFailureListener { error ->
                                onResult(emptyList(), emptyList(), 2, 0, error.message)
                            }
                    }
                    .addOnFailureListener { error ->
                        onResult(emptyList(), emptyList(), 2, 0, error.message)
                    }
            }
            .addOnFailureListener { error ->
                onResult(emptyList(), emptyList(), 2, 0, error.message)
            }
    }

    fun saveFantasyTeam(
        leagueId: String,
        startingSevenIds: List<String>,
        transfersLeft: Int,
        onResult: (Boolean, String?) -> Unit
    ) {
        val uid = auth.currentUser?.uid

        if (uid == null) {
            onResult(false, "Utilizador não autenticado")
            return
        }

        val data = hashMapOf(
            "ownerId" to uid,
            "startingSeven" to startingSevenIds,
            "transfersLeft" to transfersLeft,
            "updatedAt" to Timestamp.now()
        )

        db.collection("leagues")
            .document(leagueId)
            .collection("fantasyTeams")
            .document(uid)
            .set(data, SetOptions.merge())
            .addOnSuccessListener {
                onResult(true, null)
            }
            .addOnFailureListener { error ->
                onResult(false, error.message)
            }
    }
}