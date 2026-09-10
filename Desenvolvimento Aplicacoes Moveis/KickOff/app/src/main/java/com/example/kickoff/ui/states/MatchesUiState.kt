package com.example.kickoff.ui.states

import com.example.kickoff.model.Match

data class MatchesUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isCreator: Boolean = false,
    val currentWeek: Int = 1,
    val nextMatch: Match? = null,
    val upcomingMatches: List<Match> = emptyList(),
    val pastMatches: List<Match> = emptyList()
)