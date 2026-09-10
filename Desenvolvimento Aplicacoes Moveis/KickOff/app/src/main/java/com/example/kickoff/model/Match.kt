package com.example.kickoff.model

data class Match(
    val matchId: String = "",
    val week: Int = 1,
    val dateText: String = "",
    val timeText: String = "",
    val teamAName: String = "",
    val teamBName: String = "",
    val teamAScore: Int? = null,
    val teamBScore: Int? = null,
    val status: String = "scheduled",
    val scheduledMillis: Long = 0L,
    val userPoints: Int = 0
) {
    val isFinished: Boolean
        get() = status == "finished"
}
