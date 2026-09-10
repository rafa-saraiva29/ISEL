package com.example.kickoff.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.kickoff.data.LeagueDashboardRepository
import com.example.kickoff.ui.states.LeagueDashboardUiState

class LeagueDashboardViewModel : ViewModel() {

    private val repository = LeagueDashboardRepository()

    var uiState by mutableStateOf(LeagueDashboardUiState())
        private set

    fun loadLeagueDashboard(leagueId: String) {
        uiState = LeagueDashboardUiState(isLoading = true)

        repository.getLeagueDashboard(leagueId) { league, error ->
            uiState = if (error == null && league != null) {
                LeagueDashboardUiState(
                    isLoading = false,
                    league = league
                )
            } else {
                LeagueDashboardUiState(
                    isLoading = false,
                    errorMessage = error ?: "Erro ao carregar liga"
                )
            }
        }
    }
}