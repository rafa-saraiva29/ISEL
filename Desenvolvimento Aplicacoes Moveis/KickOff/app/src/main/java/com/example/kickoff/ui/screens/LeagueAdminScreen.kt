package com.example.kickoff.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kickoff.model.FantasyPlayer
import com.example.kickoff.ui.components.TopBar
import com.example.kickoff.ui.components.TopBarStyle
import com.example.kickoff.ui.states.LeagueAdminUiState
import com.example.kickoff.ui.theme.KickOffTheme
import com.example.kickoff.ui.theme.KickOffTypography

@Composable
fun LeagueAdminScreen(
    state: LeagueAdminUiState,
    onBackClick: () -> Unit = {},
    onAddPlayerClick: (name: String) -> Unit = {}
) {
    var name by remember { mutableStateOf("") }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar(
                title = "Administration",
                style = TopBarStyle.Child,
                onBackClick = onBackClick
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp)
                .padding(bottom = 90.dp)
        ) {
            Text(
                text = "Add Fantasy Player",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = KickOffTypography.SectionTitleSize,
                fontWeight = KickOffTypography.ExtraBold
            )

            Spacer(modifier = Modifier.height(14.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = KickOffTheme.colors.card,
                        shape = RoundedCornerShape(18.dp)
                    )
                    .padding(18.dp)
            ) {
                AdminTextField(
                    label = "PLAYER NAME",
                    value = name,
                    onValueChange = { name = it },
                    placeholder = "e.g. João Silva"
                )

                Spacer(modifier = Modifier.height(22.dp))

                Button(
                    onClick = {
                        onAddPlayerClick(name)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.background,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.PersonAdd,
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Add Player",
                            fontSize = KickOffTypography.CardTitleSize,
                            fontWeight = KickOffTypography.ExtraBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Fantasy Players",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = KickOffTypography.SectionTitleSize,
                    fontWeight = KickOffTypography.ExtraBold
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (state.players.isEmpty()) {
                    Text(
                        text = "No fantasy players added yet.",
                        color = KickOffTheme.colors.muted,
                        fontSize = KickOffTypography.BodySize,
                        fontWeight = KickOffTypography.Bold
                    )
                } else {
                    state.players.forEach { player ->
                        FantasyPlayerCard(player = player)

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

                state.errorMessage?.let {
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = KickOffTypography.SmallSize,
                        fontWeight = KickOffTypography.Bold
                    )
                }

                if (state.playerCreated) {
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Jogador criado com sucesso",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = KickOffTypography.SmallSize,
                        fontWeight = KickOffTypography.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun FantasyPlayerCard(
    player: FantasyPlayer
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = KickOffTheme.colors.input,
                shape = RoundedCornerShape(14.dp)
            )
            .padding(14.dp)
    ) {
        Text(
            text = player.name,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = KickOffTypography.BodySize,
            fontWeight = KickOffTypography.ExtraBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Points: ${player.points}  •  Goals: ${player.goals}  •  Assists: ${player.assists}",
            color = KickOffTheme.colors.muted,
            fontSize = KickOffTypography.SmallSize,
            fontWeight = KickOffTypography.Bold
        )
    }
}

@Composable
private fun AdminTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Column {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.primary,
            fontSize = KickOffTypography.LabelSize,
            fontWeight = KickOffTypography.ExtraBold
        )

        Spacer(modifier = Modifier.height(8.dp))

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
                focusedBorderColor = KickOffTheme.colors.input,
                unfocusedBorderColor = KickOffTheme.colors.input,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LeagueAdminScreenPreview() {
    KickOffTheme {
        LeagueAdminScreen(
            state = LeagueAdminUiState()
        )
    }
}