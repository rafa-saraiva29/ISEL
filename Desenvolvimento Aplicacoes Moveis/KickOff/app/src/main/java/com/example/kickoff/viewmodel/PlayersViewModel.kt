package com.example.kickoff.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.kickoff.data.PlayersRepository
import com.example.kickoff.ui.states.PlayersUiState

class PlayersViewModel : ViewModel() {

    private val repository = PlayersRepository()

    var uiState by mutableStateOf(PlayersUiState())
        private set

    fun loadPlayers(leagueId: String) {
        uiState = PlayersUiState(isLoading = true)

        repository.getFantasyPlayers(leagueId) { players, error ->
            uiState = if (error == null) {
                PlayersUiState(
                    isLoading = false,
                    players = players
                )
            } else {
                PlayersUiState(
                    isLoading = false,
                    errorMessage = error
                )
            }
        }
    }
}