package com.example.kickoff.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.kickoff.data.MatchLineupRepository
import com.example.kickoff.ui.states.MatchLineupUiState

class MatchLineupViewModel : ViewModel() {

    private val repository = MatchLineupRepository()

    var uiState by mutableStateOf(MatchLineupUiState())
        private set

    fun loadLineup(
        leagueId: String,
        matchId: String
    ) {
        uiState = MatchLineupUiState(isLoading = true)

        repository.loadMatchLineup(
            leagueId = leagueId,
            matchId = matchId
        ) { players, total, error ->
            uiState = if (error == null) {
                MatchLineupUiState(
                    isLoading = false,
                    players = players,
                    totalPoints = total
                )
            } else {
                MatchLineupUiState(
                    isLoading = false,
                    errorMessage = error
                )
            }
        }
    }
}