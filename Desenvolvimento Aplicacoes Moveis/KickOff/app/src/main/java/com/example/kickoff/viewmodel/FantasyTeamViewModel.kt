package com.example.kickoff.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.kickoff.data.FantasyTeamRepository
import com.example.kickoff.model.FantasyPlayerUi
import com.example.kickoff.ui.states.FantasyTeamUiState

class FantasyTeamViewModel : ViewModel() {

    private val repository = FantasyTeamRepository()

    var uiState by mutableStateOf(FantasyTeamUiState())
        private set

    private var allPlayers: List<FantasyPlayerUi> = emptyList()

    fun loadFantasyTeam(leagueId: String) {
        uiState = FantasyTeamUiState(isLoading = true)

        repository.loadFantasyTeamData(leagueId) { players, startingIds, transfersLeft, userLeaguePoints, error ->

            if (error != null) {
                uiState = FantasyTeamUiState(errorMessage = error)
                return@loadFantasyTeamData
            }

            allPlayers = players

            val startingSeven = List(7) { index ->
                val playerId = startingIds.getOrNull(index)
                players.find { it.playerId == playerId }
            }

            val selectedIds = startingSeven.mapNotNull { it?.playerId }
            val bench = players.filter { it.playerId !in selectedIds }

            uiState = FantasyTeamUiState(
                isLoading = false,
                totalPoints = userLeaguePoints,
                transfersLeft = transfersLeft,
                startingSeven = startingSeven,
                bench = bench
            )
        }
    }

    fun selectSlot(index: Int) {
        uiState = uiState.copy(
            selectedSlotIndex = index,
            errorMessage = null,
            saveSuccess = false
        )
    }

    fun selectBenchPlayer(player: FantasyPlayerUi) {
        val selectedIndex = uiState.selectedSlotIndex

        if (selectedIndex == null) {
            uiState = uiState.copy(
                errorMessage = "Seleciona primeiro uma posição no campo"
            )
            return
        }

        val currentStarting = uiState.startingSeven.toMutableList()
        val oldPlayer = currentStarting[selectedIndex]

        if (oldPlayer != null && uiState.transfersLeft <= 0) {
            uiState = uiState.copy(
                errorMessage = "Já não tens transfers disponíveis"
            )
            return
        }

        currentStarting[selectedIndex] = player

        val selectedIds = currentStarting.mapNotNull { it?.playerId }
        val newBench = allPlayers.filter { it.playerId !in selectedIds }

        val updatedTransfersLeft =
            if (oldPlayer == null) {
                uiState.transfersLeft
            } else {
                (uiState.transfersLeft - 1).coerceAtLeast(0)
            }

        uiState = uiState.copy(
            startingSeven = currentStarting,
            bench = newBench,
            transfersLeft = updatedTransfersLeft,
            selectedSlotIndex = null,
            errorMessage = null,
            saveSuccess = false
        )
    }

    fun saveTeam(leagueId: String) {
        val selectedPlayers = uiState.startingSeven.mapNotNull { it?.playerId }

        if (selectedPlayers.size < 7) {
            uiState = uiState.copy(
                errorMessage = "Tens de escolher 7 jogadores"
            )
            return
        }

        uiState = uiState.copy(isLoading = true)

        repository.saveFantasyTeam(
            leagueId = leagueId,
            startingSevenIds = selectedPlayers,
            transfersLeft = uiState.transfersLeft
        ) { success, error ->
            uiState = if (success) {
                uiState.copy(
                    isLoading = false,
                    saveSuccess = true,
                    errorMessage = null
                )
            } else {
                uiState.copy(
                    isLoading = false,
                    errorMessage = error ?: "Erro ao guardar equipa"
                )
            }
        }
    }

    fun clearSaveSuccess() {
        uiState = uiState.copy(saveSuccess = false)
    }
}