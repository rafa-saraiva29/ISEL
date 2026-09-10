package com.example.kickoff.ui.states

data class AuthUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val registerSuccess: Boolean = false,
    val loginSuccess: Boolean = false
)