package com.example.kickoff.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.kickoff.data.MatchReportRepository
import com.example.kickoff.ui.states.MatchReportUiState

class MatchReportViewModel : ViewModel() {

    private val repository = MatchReportRepository()

    var uiState by mutableStateOf(MatchReportUiState())
        private set

    fun loadMatchReport(leagueId: String, matchId: String) {
        uiState = MatchReportUiState(isLoading = true)

        repository.loadMatchReport(leagueId, matchId) { teamA, teamB, players, error ->
            uiState = if (error == null) {
                MatchReportUiState(
                    isLoading = false,
                    teamAName = teamA,
                    teamBName = teamB,
                    players = players
                )
            } else {
                MatchReportUiState(
                    isLoading = false,
                    errorMessage = error
                )
            }
        }
    }

    fun updateTeamAScore(value: String) {
        uiState = uiState.copy(teamAScore = value)
    }

    fun updateTeamBScore(value: String) {
        uiState = uiState.copy(teamBScore = value)
    }

    fun setPlayerTeam(playerId: String, team: String?) {
        val currentPlayers = uiState.players.toMutableList()
        val index = currentPlayers.indexOfFirst { it.playerId == playerId }

        if (index == -1) return

        val currentPlayer = currentPlayers[index]

        val isSelecting = currentPlayer.team == null && team != null
        val selectedCountForTeam = currentPlayers.count { it.team == team }

        if (isSelecting && team != null && selectedCountForTeam >= 7) {
            uiState = uiState.copy(
                errorMessage = "Só podes selecionar 7 jogadores por equipa."
            )
            return
        }

        currentPlayers[index] = currentPlayer.copy(team = team)

        uiState = uiState.copy(
            players = currentPlayers,
            errorMessage = null
        )
    }

    fun updatePlayerStat(playerId: String, stat: String, delta: Int) {
        uiState = uiState.copy(
            players = uiState.players.map { player ->
                if (player.playerId != playerId) {
                    player
                } else {
                    when (stat) {
                        "goals" -> player.copy(goals = (player.goals + delta).coerceAtLeast(0))
                        "assists" -> player.copy(assists = (player.assists + delta).coerceAtLeast(0))
                        "saves" -> player.copy(saves = (player.saves + delta).coerceAtLeast(0))
                        "goalsConceded" -> player.copy(goalsConceded = (player.goalsConceded + delta).coerceAtLeast(0))
                        "ownGoals" -> player.copy(ownGoals = (player.ownGoals + delta).coerceAtLeast(0))
                        "missedPenalties" -> player.copy(missedPenalties = (player.missedPenalties + delta).coerceAtLeast(0))
                        "savedPenalties" -> player.copy(savedPenalties = (player.savedPenalties + delta).coerceAtLeast(0))
                        else -> player
                    }
                }
            }
        )
    }

    fun submitReport(leagueId: String, matchId: String) {
        val teamAScore = uiState.teamAScore.toIntOrNull()
        val teamBScore = uiState.teamBScore.toIntOrNull()
        val selectedPlayers = uiState.players.filter { it.team != null }

        if (teamAScore == null || teamBScore == null) {
            uiState = uiState.copy(errorMessage = "Insere o resultado do jogo")
            return
        }

        if (selectedPlayers.isEmpty()) {
            uiState = uiState.copy(errorMessage = "Seleciona pelo menos um jogador")
            return
        }

        val teamAPlayers = uiState.players.filter { it.team == "A" }
        val teamBPlayers = uiState.players.filter { it.team == "B" }

        if (teamAPlayers.size != 7 || teamBPlayers.size != 7) {
            uiState = uiState.copy(
                errorMessage = "Tens de selecionar exatamente 7 jogadores para cada equipa."
            )
            return
        }

        uiState = uiState.copy(
            isLoading = true,
            errorMessage = null
        )

        repository.submitMatchReport(
            leagueId = leagueId,
            matchId = matchId,
            teamAScore = teamAScore,
            teamBScore = teamBScore,
            players = uiState.players
        ) { success, error ->
            uiState = if (success) {
                uiState.copy(
                    isLoading = false,
                    submitSuccess = true
                )
            } else {
                uiState.copy(
                    isLoading = false,
                    errorMessage = error ?: "Erro ao submeter relatório"
                )
            }
        }
    }

    fun clearSubmitSuccess() {
        uiState = uiState.copy(submitSuccess = false)
    }
}