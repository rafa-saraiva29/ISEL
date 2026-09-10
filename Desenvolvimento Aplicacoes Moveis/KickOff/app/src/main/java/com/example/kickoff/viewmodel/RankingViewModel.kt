package com.example.kickoff.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.kickoff.data.RankingRepository
import com.example.kickoff.ui.states.RankingUiState

class RankingViewModel : ViewModel() {

    private val repository = RankingRepository()

    var uiState by mutableStateOf(RankingUiState())
        private set

    fun loadRanking(leagueId: String) {
        uiState = RankingUiState(isLoading = true)

        repository.loadRanking(leagueId) { members, error ->
            uiState = if (error == null) {
                RankingUiState(
                    isLoading = false,
                    members = members
                )
            } else {
                RankingUiState(
                    isLoading = false,
                    errorMessage = error
                )
            }
        }
    }
}