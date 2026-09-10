package com.example.kickoff.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.kickoff.data.LeagueAdminRepository
import com.example.kickoff.ui.states.LeagueAdminUiState

class LeagueAdminViewModel : ViewModel() {

    private val repository = LeagueAdminRepository()

    var uiState by mutableStateOf(LeagueAdminUiState())
        private set

    fun addFantasyPlayer(
        leagueId: String,
        name: String
    ) {
        if (name.isBlank()) {
            uiState = uiState.copy(errorMessage = "Insere o nome do jogador")
            return
        }

        uiState = LeagueAdminUiState(isLoading = true)

        repository.addFantasyPlayer(
            leagueId = leagueId,
            name = name
        ) { success, error ->
            uiState = if (success) {
                LeagueAdminUiState(playerCreated = true)
            } else {
                LeagueAdminUiState(errorMessage = error ?: "Erro ao criar jogador")
            }
            if (success) {
                loadFantasyPlayers(leagueId)
                uiState = uiState.copy(playerCreated = true)
            }
        }
    }

    fun loadFantasyPlayers(leagueId: String) {
        uiState = uiState.copy(isLoading = true)

        repository.getFantasyPlayers(leagueId) { players, error ->
            uiState = if (error == null) {
                uiState.copy(
                    isLoading = false,
                    players = players
                )
            } else {
                uiState.copy(
                    isLoading = false,
                    errorMessage = error
                )
            }
        }
    }
}