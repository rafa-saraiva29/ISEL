package com.example.kickoff.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.kickoff.model.MatchLineupPlayerUi
import com.example.kickoff.ui.components.TopBar
import com.example.kickoff.ui.components.TopBarStyle
import com.example.kickoff.ui.states.MatchLineupUiState
import com.example.kickoff.ui.theme.KickOffTheme
import com.example.kickoff.ui.theme.KickOffTypography

@Composable
fun MatchLineupScreen(
    state: MatchLineupUiState,
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar(
                title = "Match Lineup",
                style = TopBarStyle.Child,
                onBackClick = onBackClick
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 18.dp)
        ) {
            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                state.errorMessage != null -> {
                    Text(
                        text = state.errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = KickOffTypography.BodySize,
                        fontWeight = KickOffTypography.Bold
                    )
                }

                else -> {
                    TotalPointsCard(totalPoints = state.totalPoints)

                    Spacer(modifier = Modifier.height(18.dp))

                    state.players.forEach { player ->
                        LineupPlayerCard(player = player)
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun TotalPointsCard(
    totalPoints: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.tertiary,
                        MaterialTheme.colorScheme.background
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "TOTAL POINTS THIS MATCH",
            color = KickOffTheme.colors.muted,
            fontSize = KickOffTypography.SmallSize,
            fontWeight = KickOffTypography.ExtraBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = totalPoints.toString(),
            color = MaterialTheme.colorScheme.primary,
            fontSize = KickOffTypography.ScreenTitleSize,
            fontWeight = KickOffTypography.ExtraBold
        )
    }
}

@Composable
private fun LineupPlayerCard(
    player: MatchLineupPlayerUi
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = KickOffTheme.colors.card,
                shape = RoundedCornerShape(14.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = player.name,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = KickOffTypography.BodySize,
            fontWeight = KickOffTypography.ExtraBold,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "${player.pointsEarned} pts",
            color = MaterialTheme.colorScheme.primary,
            fontSize = KickOffTypography.BodySize,
            fontWeight = KickOffTypography.ExtraBold
        )
    }
}