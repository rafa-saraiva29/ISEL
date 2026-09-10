package com.example.kickoff.ui.states

import com.example.kickoff.model.FantasyPlayer

data class PlayersUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val players: List<FantasyPlayer> = emptyList()
)