package com.example.kickoff.model

data class LeagueCardUi(
    val leagueId: String = "",
    val leagueName: String = "",
    val memberCount: Int = 0,
    val maxMembers: Int = 0,
    val currentWeek: Int = 1,
    val userPoints: Int = 0,
    val userRank: Int? = null
)