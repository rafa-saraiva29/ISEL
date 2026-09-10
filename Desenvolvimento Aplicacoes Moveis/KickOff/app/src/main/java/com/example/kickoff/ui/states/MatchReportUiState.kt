package com.example.kickoff.ui.states

import com.example.kickoff.model.MatchPlayerReportUi


data class MatchReportUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val submitSuccess: Boolean = false,

    val teamAName: String = "",
    val teamBName: String = "",
    val teamAScore: String = "",
    val teamBScore: String = "",

    val players: List<MatchPlayerReportUi> = emptyList()
)