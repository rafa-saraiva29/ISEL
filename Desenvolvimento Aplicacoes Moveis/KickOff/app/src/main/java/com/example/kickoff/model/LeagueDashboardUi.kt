package com.example.kickoff.model

data class LeagueDashboardUi(
    val leagueId: String = "",
    val name: String = "",
    val creatorId: String = "",
    val creatorName: String = "",
    val inviteCode: String = "",
    val memberCount: Int = 0,
    val maxMembers: Int = 0,
    val currentWeek: Int = 1,
    val userPoints: Int = 0,
    val isCreator: Boolean = false
)