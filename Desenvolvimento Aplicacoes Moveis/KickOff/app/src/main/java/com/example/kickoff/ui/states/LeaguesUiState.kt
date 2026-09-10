package com.example.kickoff.ui.states

import com.example.kickoff.model.LeagueCardUi

data class LeaguesUiState(
    val isLoading: Boolean = false,
    val leagues: List<LeagueCardUi> = emptyList(),
    val errorMessage: String? = null,
    val createSuccess: Boolean = false,
    val joinSuccess: Boolean = false
)