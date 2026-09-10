package com.example.kickoff.model

data class ProfileUi(
    val userId: String = "",
    val fullName: String = "",
    val email: String = "",
    val globalPoints: Int = 0,
    val globalRank: Int? = null,
    val leaguesCount: Int = 0,
    val matchesPlayed: Int = 0,
    val averagePoints: Double = 0.0
)