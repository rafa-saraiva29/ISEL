package com.example.kickoff.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.kickoff.data.ProfileRepository
import com.example.kickoff.ui.states.ProfileUiState

class ProfileViewModel : ViewModel() {

    private val repository = ProfileRepository()

    var uiState by mutableStateOf(ProfileUiState())
        private set

    fun loadProfile() {
        uiState = ProfileUiState(isLoading = true)

        repository.loadProfile { profile, error ->
            uiState = if (error == null && profile != null) {
                ProfileUiState(
                    isLoading = false,
                    profile = profile
                )
            } else {
                ProfileUiState(
                    isLoading = false,
                    errorMessage = error ?: "Erro ao carregar perfil"
                )
            }
        }
    }

    fun logout() {
        repository.logout()
        uiState = ProfileUiState(logoutSuccess = true)
    }

    fun clearLogoutSuccess() {
        uiState = uiState.copy(logoutSuccess = false)
    }
}