package com.example.kickoff.model

data class MatchPlayerReportUi(
    val playerId: String = "",
    val name: String = "",
    val team: String? = null,

    val goals: Int = 0,
    val assists: Int = 0,
    val ownGoals: Int = 0,
    val missedPenalties: Int = 0,
    val savedPenalties: Int = 0,
    val saves: Int = 0,
    val goalsConceded: Int = 0
) {
    val played: Boolean
        get() = team != null
}