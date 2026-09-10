package com.example.kickoff.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kickoff.model.LeagueCardUi
import com.example.kickoff.ui.components.ExpandableFab
import com.example.kickoff.ui.components.FabAction
import com.example.kickoff.ui.components.TopBar
import com.example.kickoff.ui.components.TopBarStyle
import com.example.kickoff.ui.states.LeaguesUiState
import com.example.kickoff.ui.theme.KickOffTheme
import com.example.kickoff.ui.theme.KickOffTypography

@Composable
fun LeaguesScreen(
    state: LeaguesUiState,
    onOpenLeagueClick: (LeagueCardUi) -> Unit = {},
    onCreateLeagueClick: () -> Unit = {},
    onJoinLeagueClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar(
                title = "Leagues",
                style = TopBarStyle.Root
            )
        },
        floatingActionButton = {
            ExpandableFab(
                actions = listOf(
                    FabAction(
                        text = "Create League",
                        icon = Icons.Default.SportsSoccer,
                        onClick = onCreateLeagueClick
                    ),
                    FabAction(
                        text = "Join League",
                        icon = Icons.Default.GroupAdd,
                        onClick = onJoinLeagueClick
                    )
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(bottom = 90.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "My Leagues",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = KickOffTypography.SectionTitleSize,
                        fontWeight = KickOffTypography.ExtraBold,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = "${state.leagues.size} LEAGUES",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = KickOffTypography.SmallSize,
                        fontWeight = KickOffTypography.ExtraBold
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
            }

            if (state.isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            state.errorMessage?.let { message ->
                item {
                    Text(
                        text = message,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = KickOffTypography.BodySize,
                        fontWeight = KickOffTypography.Bold
                    )
                }
            }

            if (!state.isLoading && state.leagues.isEmpty()) {
                item {
                    Text(
                        text = "You are not in any league yet.",
                        color = KickOffTheme.colors.muted,
                        fontSize = KickOffTypography.BodySize,
                        fontWeight = KickOffTypography.Bold
                    )
                }
            }

            items(state.leagues) { league ->
                LeagueCard(
                    league = league,
                    onOpenClick = {
                        onOpenLeagueClick(league)
                    }
                )

                Spacer(modifier = Modifier.height(14.dp))
            }
        }
    }
}

@Composable
private fun LeagueCard(
    league: LeagueCardUi,
    onOpenClick: () -> Unit
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
                shape = RoundedCornerShape(18.dp)
            )
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = league.leagueName,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = KickOffTypography.CardTitleSize,
                    fontWeight = KickOffTypography.ExtraBold
                )
            }

            Box(
                modifier = Modifier
                    .background(
                        color = KickOffTheme.colors.input,
                        shape = RoundedCornerShape(50.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 7.dp)
            ) {
                Text(
                    text = league.userRank?.let { "#$it" } ?: "-",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = KickOffTypography.LabelSize,
                    fontWeight = KickOffTypography.ExtraBold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            StatBox(
                title = "MEMBERS",
                value = "${league.memberCount}/${league.maxMembers}",
                modifier = Modifier.weight(1f)
            )

            StatBox(
                title = "POINTS",
                value = league.userPoints.toString(),
                modifier = Modifier.weight(1f)
            )

            StatBox(
                title = "WEEK",
                value = league.currentWeek.toString().padStart(2, '0'),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Button(
            onClick = onOpenClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = KickOffTheme.colors.input,
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = "Open League",
                fontSize = KickOffTypography.BodySize,
                fontWeight = KickOffTypography.ExtraBold
            )

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun StatBox(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .height(56.dp)
            .background(
                color = KickOffTheme.colors.input,
                shape = RoundedCornerShape(8.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            color = KickOffTheme.colors.muted,
            fontSize = KickOffTypography.LabelSize,
            fontWeight = KickOffTypography.ExtraBold
        )

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = KickOffTypography.BodySize,
            fontWeight = KickOffTypography.ExtraBold
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LeaguesScreenPreview() {
    KickOffTheme {
        LeaguesScreen(
            state = LeaguesUiState()
        )
    }
}