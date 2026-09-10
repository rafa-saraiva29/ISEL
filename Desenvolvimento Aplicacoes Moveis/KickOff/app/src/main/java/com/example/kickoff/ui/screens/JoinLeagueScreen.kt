package com.example.kickoff.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kickoff.ui.components.TopBar
import com.example.kickoff.ui.components.TopBarStyle
import com.example.kickoff.ui.states.LeaguesUiState
import com.example.kickoff.ui.theme.KickOffTheme
import com.example.kickoff.ui.theme.KickOffTypography

@Composable
fun JoinLeagueScreen(
    state: LeaguesUiState,
    onBackClick: () -> Unit = {},
    onJoinLeagueClick: (String, String) -> Unit = { _, _ -> }
) {
    var leagueName by remember { mutableStateOf("") }
    var inviteCode by remember { mutableStateOf("") }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar(
                title = "Join League",
                style = TopBarStyle.Child,
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    onJoinLeagueClick(leagueName, inviteCode)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp, vertical = 16.dp)
                    .height(54.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.background
                )
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.background,
                        modifier = Modifier.size(22.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(Icons.Default.GroupAdd, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Join League",
                        fontWeight = KickOffTypography.ExtraBold
                    )
                }
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp)
        ) {
            JoinInputCard(label = "LEAGUE NAME") {
                JoinTextField(
                    value = leagueName,
                    onValueChange = { leagueName = it },
                    placeholder = "e.g. Peladinhas 2026"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            JoinInputCard(label = "INVITE CODE") {
                JoinTextField(
                    value = inviteCode,
                    onValueChange = { inviteCode = it },
                    placeholder = "e.g. KICKOFF2026"
                )
            }

            state.errorMessage?.let {
                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = KickOffTypography.BodySize,
                    fontWeight = KickOffTypography.Bold
                )
            }
        }
    }
}

@Composable
private fun JoinInputCard(
    label: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = KickOffTheme.colors.card,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.primary,
            fontSize = KickOffTypography.LabelSize,
            fontWeight = KickOffTypography.ExtraBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        content()
    }
}

@Composable
private fun JoinTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        singleLine = true,
        placeholder = {
            Text(
                text = placeholder,
                color = KickOffTheme.colors.muted,
                fontSize = KickOffTypography.BodySize,
                fontWeight = KickOffTypography.Bold
            )
        },
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedContainerColor = KickOffTheme.colors.input,
            unfocusedContainerColor = KickOffTheme.colors.input,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = KickOffTheme.colors.input,
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}