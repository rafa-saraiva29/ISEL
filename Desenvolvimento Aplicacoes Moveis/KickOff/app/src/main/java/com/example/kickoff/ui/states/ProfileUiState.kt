package com.example.kickoff.ui.states

import com.example.kickoff.model.ProfileUi

data class ProfileUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val profile: ProfileUi? = null,
    val logoutSuccess: Boolean = false
)