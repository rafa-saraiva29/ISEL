package com.example.kickoff.ui.states

import com.example.kickoff.model.FantasyPlayer

data class LeagueAdminUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val playerCreated: Boolean = false,
    val players: List<FantasyPlayer> = emptyList()
)