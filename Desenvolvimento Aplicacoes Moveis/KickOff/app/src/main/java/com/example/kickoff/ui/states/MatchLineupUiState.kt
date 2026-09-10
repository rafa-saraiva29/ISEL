package com.example.kickoff.ui.states

import com.example.kickoff.model.MatchLineupPlayerUi

data class MatchLineupUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val players: List<MatchLineupPlayerUi> = emptyList(),
    val totalPoints: Int = 0
)