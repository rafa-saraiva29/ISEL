package com.example.kickoff.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.kickoff.data.AuthRepository
import com.example.kickoff.ui.states.AuthUiState

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    var uiState by mutableStateOf(AuthUiState())
        private set

    fun isUserLoggedIn(): Boolean {
        return repository.isUserLoggedIn()
    }

    fun register(
        fullName: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        if (
            fullName.isBlank() ||
            email.isBlank() ||
            password.isBlank() ||
            confirmPassword.isBlank()
        ) {
            uiState = AuthUiState(errorMessage = "Preenche todos os campos")
            return
        }

        if (password != confirmPassword) {
            uiState = AuthUiState(errorMessage = "As passwords não coincidem")
            return
        }

        uiState = AuthUiState(isLoading = true)

        repository.register(fullName, email, password) { success, error ->
            uiState = if (success) {
                AuthUiState(registerSuccess = true)
            } else {
                AuthUiState(errorMessage = error ?: "Erro ao criar conta")
            }
        }
    }

    fun login(
        email: String,
        password: String
    ) {
        if (email.isBlank() || password.isBlank()) {
            uiState = AuthUiState(errorMessage = "Preenche todos os campos")
            return
        }

        uiState = AuthUiState(isLoading = true)

        repository.login(email, password) { success, error ->
            uiState = if (success) {
                AuthUiState(loginSuccess = true)
            } else {
                AuthUiState(errorMessage = error ?: "Erro ao fazer login")
            }
        }
    }

    fun loginWithGoogle(idToken: String) {
        uiState = uiState.copy(
            isLoading = true,
            errorMessage = null
        )

        repository.signInWithGoogle(idToken) { success, error ->
            uiState = if (success) {
                uiState.copy(
                    isLoading = false,
                    loginSuccess = true,
                    errorMessage = null
                )
            } else {
                uiState.copy(
                    isLoading = false,
                    errorMessage = error ?: "Erro ao iniciar sessão com Google"
                )
            }
        }
    }
}