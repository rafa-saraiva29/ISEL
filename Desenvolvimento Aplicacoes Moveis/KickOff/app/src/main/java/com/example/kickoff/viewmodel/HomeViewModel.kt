package com.example.kickoff.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.kickoff.data.HomeRepository
import com.example.kickoff.ui.states.HomeUiState

class HomeViewModel : ViewModel() {

    private val repository = HomeRepository()

    var uiState by mutableStateOf(HomeUiState())
        private set

    fun loadNextMatch() {
        uiState = uiState.copy(isLoading = true, errorMessage = null)

        repository.loadNextMatch { match, error ->
            uiState = if (error == null) {
                HomeUiState(
                    isLoading = false,
                    nextMatch = match
                )
            } else {
                HomeUiState(
                    isLoading = false,
                    errorMessage = error
                )
            }
        }
    }
}