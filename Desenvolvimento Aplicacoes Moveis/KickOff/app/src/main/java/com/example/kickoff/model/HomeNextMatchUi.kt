package com.example.kickoff.model

data class HomeNextMatchUi(
    val leagueId: String = "",
    val matchId: String = "",
    val leagueName: String = "",
    val teamAName: String = "",
    val teamBName: String = "",
    val dateText: String = "",
    val timeText: String = "",
    val location: String = "",
    val scheduledMillis: Long = 0L
)