package com.example.kickoff.ui.states

import com.example.kickoff.model.FantasyPlayerUi

data class FantasyTeamUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val saveSuccess: Boolean = false,

    val totalPoints: Int = 0,
    val transfersLeft: Int = 2,

    val startingSeven: List<FantasyPlayerUi?> = List(7) { null },
    val bench: List<FantasyPlayerUi> = emptyList(),

    val selectedSlotIndex: Int? = null
)