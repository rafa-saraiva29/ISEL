package com.example.kickoff.ui.states

import com.example.kickoff.model.RankingMemberUi

data class RankingUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val members: List<RankingMemberUi> = emptyList()
)