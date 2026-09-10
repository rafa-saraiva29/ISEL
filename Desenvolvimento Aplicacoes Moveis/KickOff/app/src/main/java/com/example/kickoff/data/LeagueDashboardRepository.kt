package com.example.kickoff.data

import com.example.kickoff.model.LeagueDashboardUi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LeagueDashboardRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun getLeagueDashboard(
        leagueId: String,
        onResult: (LeagueDashboardUi?, String?) -> Unit
    ) {
        val uid = auth.currentUser?.uid

        if (uid == null) {
            onResult(null, "Utilizador não autenticado")
            return
        }

        val leagueRef = db.collection("leagues").document(leagueId)

        leagueRef.get()
            .addOnSuccessListener { leagueDoc ->

                if (!leagueDoc.exists()) {
                    onResult(null, "Liga não encontrada")
                    return@addOnSuccessListener
                }

                val name = leagueDoc.getString("name") ?: ""
                val creatorId = leagueDoc.getString("creatorId") ?: ""
                val inviteCode = leagueDoc.getString("inviteCode") ?: ""
                val memberCount = leagueDoc.getLong("memberCount")?.toInt() ?: 0
                val maxMembers = leagueDoc.getLong("maxMembers")?.toInt() ?: 0
                val currentWeek = leagueDoc.getLong("currentWeek")?.toInt() ?: 1

                db.collection("leagues")
                    .document(leagueId)
                    .collection("members")
                    .document(uid)
                    .get()
                    .addOnSuccessListener { memberDoc ->

                        val userPoints =
                            memberDoc.getLong("points")?.toInt() ?: 0

                        db.collection("users")
                            .document(creatorId)
                            .get()
                            .addOnSuccessListener { creatorDoc ->

                                val creatorName =
                                    creatorDoc.getString("fullName") ?: "Unknown"

                                val dashboard = LeagueDashboardUi(
                                    leagueId = leagueId,
                                    name = name,
                                    creatorId = creatorId,
                                    creatorName = creatorName,
                                    inviteCode = inviteCode,
                                    memberCount = memberCount,
                                    maxMembers = maxMembers,
                                    currentWeek = currentWeek,
                                    userPoints = userPoints,
                                    isCreator = uid == creatorId
                                )

                                onResult(dashboard, null)
                            }
                            .addOnFailureListener { error ->
                                onResult(null, error.message)
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