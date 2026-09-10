package com.example.kickoff.data

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.example.kickoff.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import java.security.MessageDigest
import java.util.UUID

class GoogleSignInHelper(
    private val context: Context
) {
    private val credentialManager = CredentialManager.create(context)

    suspend fun getGoogleIdToken(): String? {
        return try {
            val rawNonce = UUID.randomUUID().toString()
            val hashedNonce = rawNonce.sha256()

            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(context.getString(R.string.default_web_client_id))
                .setNonce(hashedNonce)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(
                context = context,
                request = request
            )

            val credential = result.credential

            val googleIdTokenCredential =
                GoogleIdTokenCredential.createFrom(credential.data)

            googleIdTokenCredential.idToken

        }catch (e: GetCredentialException) {
            Log.e("GoogleSignIn", "Credential error type: ${e.type}", e)
            null
        } catch (e: Exception) {
            Log.e("GoogleSignIn", "Unexpected error: ${e.message}", e)
            null
        }
    }
}

private fun String.sha256(): String {
    val bytes = MessageDigest
        .getInstance("SHA-256")
        .digest(toByteArray())

    return bytes.joinToString("") { "%02x".format(it) }
}