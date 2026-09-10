package com.example.kickoff.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.kickoff.data.MatchesRepository
import com.example.kickoff.ui.states.CreateMatchUiState
import com.example.kickoff.ui.states.MatchesUiState

class MatchesViewModel : ViewModel() {

    private val repository = MatchesRepository()

    var uiState by mutableStateOf(MatchesUiState())
        private set

    var createMatchState by mutableStateOf(CreateMatchUiState())
        private set

    fun loadMatches(leagueId: String) {
        uiState = uiState.copy(isLoading = true, errorMessage = null)

        repository.getLeagueCurrentWeek(leagueId) { week, weekError ->

            if (weekError != null) {
                uiState = MatchesUiState(
                    isLoading = false,
                    errorMessage = weekError
                )
                return@getLeagueCurrentWeek
            }

            repository.getMatches(leagueId) { isCreator, nextMatch, upcomingMatches, pastMatches, error ->
                uiState = if (error == null) {
                    MatchesUiState(
                        isLoading = false,
                        isCreator = isCreator,
                        currentWeek = week,
                        nextMatch = nextMatch,
                        upcomingMatches = upcomingMatches,
                        pastMatches = pastMatches
                    )
                } else {
                    MatchesUiState(
                        isLoading = false,
                        errorMessage = error
                    )
                }
            }
        }
    }

    fun loadCreateMatchData(leagueId: String) {
        createMatchState = CreateMatchUiState(isLoading = true)

        repository.getLeagueCurrentWeek(leagueId) { week, error ->
            createMatchState = if (error == null) {
                CreateMatchUiState(
                    isLoading = false,
                    currentWeek = week
                )
            } else {
                CreateMatchUiState(
                    isLoading = false,
                    errorMessage = error
                )
            }
        }
    }

    fun createMatch(
        leagueId: String,
        weekText: String,
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        minute: Int,
        teamAName: String,
        teamBName: String,
        location: String,
        notes: String
    ) {
        val week = weekText.toIntOrNull()

        if (week == null || week <= 0) {
            createMatchState = CreateMatchUiState(errorMessage = "Semana inválida")
            return
        }

        if (teamAName.isBlank() || teamBName.isBlank()) {
            createMatchState = CreateMatchUiState(errorMessage = "Preenche o nome das equipas")
            return
        }

        createMatchState = CreateMatchUiState(isLoading = true)

        repository.createMatch(
            leagueId = leagueId,
            week = week,
            year = year,
            month = month,
            day = day,
            hour = hour,
            minute = minute,
            teamAName = teamAName,
            teamBName = teamBName,
            location = location,
            notes = notes
        ) { success, error ->
            createMatchState = if (success) {
                CreateMatchUiState(createSuccess = true)
            } else {
                CreateMatchUiState(errorMessage = error ?: "Erro ao criar jogo")
            }
        }
    }

    fun clearCreateMatchSuccess() {
        createMatchState = createMatchState.copy(createSuccess = false)
    }
}