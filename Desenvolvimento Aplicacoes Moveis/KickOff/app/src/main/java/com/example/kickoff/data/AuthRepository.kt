package com.example.kickoff.data

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun logout() {
        auth.signOut()
    }
    fun register(
        fullName: String,
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid

                if (uid == null) {
                    onResult(false, "Erro ao obter utilizador")
                    return@addOnSuccessListener
                }

                val user = hashMapOf(
                    "uid" to uid,
                    "fullName" to fullName,
                    "email" to email,
                    "createdAt" to Timestamp.now()
                )

                db.collection("users")
                    .document(uid)
                    .set(user)
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

    fun login(
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    fun signInWithGoogle(
        idToken: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        val credential = com.google.firebase.auth.GoogleAuthProvider
            .getCredential(idToken, null)

        auth.signInWithCredential(credential)
            .addOnSuccessListener { result ->
                val user = result.user

                if (user == null) {
                    onResult(false, "Erro ao obter utilizador Google")
                    return@addOnSuccessListener
                }

                val userRef = db.collection("users").document(user.uid)

                userRef.get()
                    .addOnSuccessListener { doc ->

                        if (doc.exists()) {
                            onResult(true, null)
                        } else {
                            val userData = hashMapOf(
                                "userId" to user.uid,
                                "fullName" to (user.displayName ?: ""),
                                "email" to (user.email ?: ""),
                                "globalPoints" to 0,
                                "matchesPlayed" to 0,
                                "createdAt" to Timestamp.now()
                            )

                            userRef.set(userData)
                                .addOnSuccessListener {
                                    onResult(true, null)
                                }
                                .addOnFailureListener { error ->
                                    onResult(false, error.message)
                                }
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