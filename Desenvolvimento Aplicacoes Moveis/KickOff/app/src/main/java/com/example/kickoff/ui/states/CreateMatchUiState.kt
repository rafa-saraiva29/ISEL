package com.example.kickoff.ui.states

data class CreateMatchUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val createSuccess: Boolean = false,
    val currentWeek: Int = 1
)