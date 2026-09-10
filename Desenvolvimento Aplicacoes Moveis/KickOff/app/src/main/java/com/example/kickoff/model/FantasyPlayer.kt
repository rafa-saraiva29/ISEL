package com.example.kickoff.model

data class FantasyPlayer(
    val playerId: String = "",
    val name: String = "",

    val played: Int = 0,
    val goals: Int = 0,
    val assists: Int = 0,
    val ownGoals: Int = 0,
    val missedPenalties: Int = 0,
    val savedPenalties: Int = 0,
    val saves: Int = 0,
    val goalsConceded: Int = 0,

    val points: Int = 0
)