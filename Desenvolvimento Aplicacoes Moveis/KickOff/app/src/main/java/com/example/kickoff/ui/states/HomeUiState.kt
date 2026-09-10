package com.example.kickoff.ui.states

import com.example.kickoff.model.HomeNextMatchUi

data class HomeUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val nextMatch: HomeNextMatchUi? = null
)