package com.example.kickoff.ui.states

import com.example.kickoff.model.LeagueDashboardUi

data class LeagueDashboardUiState(
    val isLoading: Boolean = false,
    val league: LeagueDashboardUi? = null,
    val errorMessage: String? = null
)