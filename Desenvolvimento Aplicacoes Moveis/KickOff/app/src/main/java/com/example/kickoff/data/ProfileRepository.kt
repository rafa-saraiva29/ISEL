package com.example.kickoff.data

import com.example.kickoff.model.ProfileUi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ProfileRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun loadProfile(
        onResult: (ProfileUi?, String?) -> Unit
    ) {
        val uid = auth.currentUser?.uid

        if (uid == null) {
            onResult(null, "Utilizador não autenticado")
            return
        }

        db.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { userDoc ->

                val fullName = userDoc.getString("fullName") ?: "Unknown"
                val email = userDoc.getString("email") ?: auth.currentUser?.email.orEmpty()
                val globalPoints = userDoc.getLong("globalPoints")?.toInt() ?: 0
                val matchesPlayed =
                    userDoc.getLong("matchesPlayed")?.toInt() ?: 0

                val averagePoints =
                    if (matchesPlayed == 0) {
                        0.0
                    } else {
                        globalPoints.toDouble() / matchesPlayed
                    }

                db.collection("users")
                    .orderBy("globalPoints", Query.Direction.DESCENDING)
                    .get()
                    .addOnSuccessListener { usersSnapshot ->

                        val rankIndex = usersSnapshot.documents.indexOfFirst {
                            it.id == uid
                        }

                        val globalRank =
                            if (rankIndex >= 0) rankIndex + 1 else null

                        db.collection("users")
                            .document(uid)
                            .collection("leagues")
                            .get()
                            .addOnSuccessListener { leaguesSnapshot ->

                                onResult(
                                    ProfileUi(
                                        userId = uid,
                                        fullName = fullName,
                                        email = email,
                                        globalPoints = globalPoints,
                                        globalRank = globalRank,
                                        leaguesCount = leaguesSnapshot.size(),
                                        matchesPlayed = matchesPlayed,
                                        averagePoints = averagePoints
                                    ),
                                    null
                                )
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

    fun logout() {
        auth.signOut()
    }
}