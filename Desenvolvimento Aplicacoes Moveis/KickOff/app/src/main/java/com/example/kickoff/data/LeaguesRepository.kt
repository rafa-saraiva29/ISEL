package com.example.kickoff.data

import com.example.kickoff.model.LeagueCardUi
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.FieldPath

class LeaguesRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun getUserLeagues(
        onResult: (List<LeagueCardUi>, String?) -> Unit
    ) {
        val uid = auth.currentUser?.uid

        if (uid == null) {
            onResult(emptyList(), "Utilizador não autenticado")
            return
        }

        db.collection("users")
            .document(uid)
            .collection("leagues")
            .get()
            .addOnSuccessListener { userLeaguesSnapshot ->

                val leagueIds = userLeaguesSnapshot.documents.map { it.id }

                if (leagueIds.isEmpty()) {
                    onResult(emptyList(), null)
                    return@addOnSuccessListener
                }

                loadLeagueCards(
                    uid = uid,
                    leagueIds = leagueIds,
                    onResult = onResult
                )
            }
            .addOnFailureListener { error ->
                onResult(emptyList(), error.message)
            }
    }

    private fun loadLeagueCards(
        uid: String,
        leagueIds: List<String>,
        onResult: (List<LeagueCardUi>, String?) -> Unit
    ) {
        db.collection("leagues")
            .whereIn(FieldPath.documentId(), leagueIds)
            .get()
            .addOnSuccessListener { leaguesSnapshot ->

                val result = mutableListOf<LeagueCardUi>()
                var completed = 0

                if (leaguesSnapshot.isEmpty) {
                    onResult(emptyList(), null)
                    return@addOnSuccessListener
                }

                leaguesSnapshot.documents.forEach { leagueDoc ->

                    val leagueId = leagueDoc.id
                    val leagueName = leagueDoc.getString("name") ?: ""
                    val memberCount = leagueDoc.getLong("memberCount")?.toInt() ?: 0
                    val maxMembers = leagueDoc.getLong("maxMembers")?.toInt() ?: 0
                    val currentWeek = leagueDoc.getLong("currentWeek")?.toInt() ?: 1

                    db.collection("leagues")
                        .document(leagueId)
                        .collection("members")
                        .orderBy("points", Query.Direction.DESCENDING)
                        .get()
                        .addOnSuccessListener { membersSnapshot ->

                            val members = membersSnapshot.documents

                            val userIndex = members.indexOfFirst { it.id == uid }

                            val userRank =
                                if (userIndex >= 0) userIndex + 1 else null

                            val userPoints =
                                members.find { it.id == uid }
                                    ?.getLong("points")
                                    ?.toInt()
                                    ?: 0

                            result.add(
                                LeagueCardUi(
                                    leagueId = leagueId,
                                    leagueName = leagueName,
                                    memberCount = memberCount,
                                    maxMembers = maxMembers,
                                    currentWeek = currentWeek,
                                    userPoints = userPoints,
                                    userRank = userRank
                                )
                            )

                            completed++

                            if (completed == leaguesSnapshot.size()) {
                                onResult(result, null)
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

    fun createLeague(
        leagueName: String,
        maxMembers: Int,
        inviteCode: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        val uid = auth.currentUser?.uid

        if (uid == null) {
            onResult(false, "Utilizador não autenticado")
            return
        }

        val leagueRef = db.collection("leagues").document()
        val leagueId = leagueRef.id

        val leagueData = hashMapOf(
            "name" to leagueName,
            "creatorId" to uid,
            "inviteCode" to inviteCode,
            "maxMembers" to maxMembers,
            "memberCount" to 1,
            "currentWeek" to 1,
            "createdAt" to Timestamp.now()
        )

        val memberData = hashMapOf(
            "userId" to uid,
            "points" to 0,
            "role" to "owner",
            "joinedAt" to Timestamp.now()
        )

        val userLeagueRef = hashMapOf(
            "leagueId" to leagueId,
            "joinedAt" to Timestamp.now()
        )

        db.runBatch { batch ->
            batch.set(leagueRef, leagueData)

            batch.set(
                db.collection("leagues")
                    .document(leagueId)
                    .collection("members")
                    .document(uid),
                memberData
            )

            batch.set(
                db.collection("users")
                    .document(uid)
                    .collection("leagues")
                    .document(leagueId),
                userLeagueRef
            )
        }.addOnSuccessListener {
            onResult(true, null)
        }.addOnFailureListener { error ->
            onResult(false, error.message)
        }
    }

    fun joinLeague(
        leagueName: String,
        inviteCode: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        val uid = auth.currentUser?.uid

        if (uid == null) {
            onResult(false, "Utilizador não autenticado")
            return
        }

        db.collection("leagues")
            .whereEqualTo("name", leagueName.trim())
            .whereEqualTo("inviteCode", inviteCode.trim())
            .get()
            .addOnSuccessListener { snapshot ->

                if (snapshot.isEmpty) {
                    onResult(false, "Liga não encontrada")
                    return@addOnSuccessListener
                }

                val leagueDoc = snapshot.documents.first()
                val leagueId = leagueDoc.id

                val memberCount = leagueDoc.getLong("memberCount")?.toInt() ?: 0
                val maxMembers = leagueDoc.getLong("maxMembers")?.toInt() ?: 0

                if (memberCount >= maxMembers) {
                    onResult(false, "Esta liga já está cheia")
                    return@addOnSuccessListener
                }

                val leagueRef = db.collection("leagues").document(leagueId)
                val memberRef = leagueRef.collection("members").document(uid)
                val userLeagueRef = db.collection("users")
                    .document(uid)
                    .collection("leagues")
                    .document(leagueId)

                memberRef.get()
                    .addOnSuccessListener { memberDoc ->

                        if (memberDoc.exists()) {
                            onResult(false, "Já pertences a esta liga")
                            return@addOnSuccessListener
                        }

                        db.runBatch { batch ->

                            batch.set(
                                memberRef,
                                mapOf(
                                    "userId" to uid,
                                    "points" to 0,
                                    "role" to "member",
                                    "joinedAt" to Timestamp.now()
                                )
                            )

                            batch.set(
                                userLeagueRef,
                                mapOf(
                                    "leagueId" to leagueId,
                                    "joinedAt" to Timestamp.now()
                                )
                            )

                            batch.update(
                                leagueRef,
                                "memberCount",
                                com.google.firebase.firestore.FieldValue.increment(1)
                            )

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
            .addOnFailureListener { error ->
                onResult(false, error.message)
            }
    }
}