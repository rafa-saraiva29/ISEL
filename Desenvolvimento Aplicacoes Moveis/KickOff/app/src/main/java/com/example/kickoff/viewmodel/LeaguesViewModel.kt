package com.example.kickoff.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.kickoff.data.LeaguesRepository
import com.example.kickoff.ui.states.LeaguesUiState

class LeaguesViewModel : ViewModel() {

    private val repository = LeaguesRepository()

    var uiState by mutableStateOf(LeaguesUiState())
        private set

    fun loadUserLeagues() {
        uiState = uiState.copy(
            isLoading = true,
            errorMessage = null
        )

        repository.getUserLeagues { leagues, error ->
            uiState = if (error == null) {
                LeaguesUiState(
                    isLoading = false,
                    leagues = leagues
                )
            } else {
                LeaguesUiState(
                    isLoading = false,
                    errorMessage = error
                )
            }
        }
    }

    fun createLeague(
        leagueName: String,
        maxMembers: Int,
        inviteCode: String
    ) {
        if (leagueName.isBlank()) {
            uiState = uiState.copy(errorMessage = "Insere o nome da liga")
            return
        }

        if (inviteCode.isBlank()) {
            uiState = uiState.copy(errorMessage = "Insere um código de convite")
            return
        }

        uiState = uiState.copy(
            isLoading = true,
            errorMessage = null,
            createSuccess = false
        )

        repository.createLeague(
            leagueName = leagueName,
            maxMembers = maxMembers,
            inviteCode = inviteCode
        ) { success, error ->
            if (success) {
                uiState = uiState.copy(
                    isLoading = false,
                    createSuccess = true
                )

                loadUserLeagues()
            } else {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = error ?: "Erro ao criar liga"
                )
            }
        }
    }

    fun joinLeague(
        leagueName: String,
        inviteCode: String
    ) {
        if (leagueName.isBlank() || inviteCode.isBlank()) {
            uiState = uiState.copy(
                errorMessage = "Preenche o nome da liga e o código de convite"
            )
            return
        }

        uiState = uiState.copy(
            isLoading = true,
            errorMessage = null
        )

        repository.joinLeague(
            leagueName = leagueName,
            inviteCode = inviteCode
        ) { success, error ->
            uiState = if (success) {
                uiState.copy(
                    isLoading = false,
                    joinSuccess = true,
                    errorMessage = null
                )
            } else {
                uiState.copy(
                    isLoading = false,
                    errorMessage = error ?: "Erro ao entrar na liga"
                )
            }
        }
    }

    fun clearJoinSuccess() {
        uiState = uiState.copy(joinSuccess = false)
    }

    fun clearCreateSuccess() {
        uiState = uiState.copy(createSuccess = false)
    }
}