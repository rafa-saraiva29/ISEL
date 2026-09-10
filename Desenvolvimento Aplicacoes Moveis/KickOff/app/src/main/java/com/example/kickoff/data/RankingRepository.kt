package com.example.kickoff.data

import com.example.kickoff.model.RankingMemberUi
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class RankingRepository {

    private val db = FirebaseFirestore.getInstance()

    fun loadRanking(
        leagueId: String,
        onResult: (List<RankingMemberUi>, String?) -> Unit
    ) {
        db.collection("leagues")
            .document(leagueId)
            .collection("members")
            .orderBy("points", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { snapshot ->
                val membersDocs = snapshot.documents

                if (membersDocs.isEmpty()) {
                    onResult(emptyList(), null)
                    return@addOnSuccessListener
                }

                val result = mutableListOf<RankingMemberUi>()
                var completed = 0

                membersDocs.forEachIndexed { index, memberDoc ->
                    val userId = memberDoc.id
                    val points = memberDoc.getLong("points")?.toInt() ?: 0

                    db.collection("users")
                        .document(userId)
                        .get()
                        .addOnSuccessListener { userDoc ->
                            val name =
                                userDoc.getString("fullName")
                                    ?: userDoc.getString("email")
                                    ?: "Unknown"

                            result.add(
                                RankingMemberUi(
                                    userId = userId,
                                    name = name,
                                    points = points,
                                    rank = index + 1
                                )
                            )

                            completed++

                            if (completed == membersDocs.size) {
                                onResult(
                                    result.sortedBy { it.rank },
                                    null
                                )
                            }
                        }
                        .addOnFailureListener { error ->
                            onResult(emptyList(), error.message)
                        }
                }
            }
            .addOnFailureListener { error ->
                onResult(emptyList(), error.message)
            }
    }
}