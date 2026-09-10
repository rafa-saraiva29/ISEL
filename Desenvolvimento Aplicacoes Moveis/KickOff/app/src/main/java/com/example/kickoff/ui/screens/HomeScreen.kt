@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.kickoff.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kickoff.model.HomeNextMatchUi
import com.example.kickoff.model.LeagueCardUi
import com.example.kickoff.ui.components.TopBar
import com.example.kickoff.ui.components.TopBarStyle
import com.example.kickoff.ui.states.HomeUiState
import com.example.kickoff.ui.states.LeaguesUiState
import com.example.kickoff.ui.theme.KickOffTheme
import com.example.kickoff.ui.theme.KickOffTypography

@Composable
fun HomeScreen(
    homeState: HomeUiState,
    leaguesState: LeaguesUiState,
    onOpenNextMatchClick: (String) -> Unit = {},
    onOpenLeagueClick: (LeagueCardUi) -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar(
                title = "KickOff",
                style = TopBarStyle.Root
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 18.dp),
            contentPadding = PaddingValues(bottom = 90.dp)
        ) {
            item {
                SectionHeader(title = "Next Match")

                Spacer(modifier = Modifier.height(14.dp))

                when {
                    homeState.isLoading -> {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    homeState.nextMatch == null -> {
                        Text(
                            text = "No upcoming matches.",
                            color = KickOffTheme.colors.muted,
                            fontSize = KickOffTypography.BodySize,
                            fontWeight = KickOffTypography.Bold
                        )
                    }

                    else -> {
                        NextMatchCard(
                            match = homeState.nextMatch,
                            onOpenClick = {
                                onOpenNextMatchClick(homeState.nextMatch.leagueId)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                SectionHeader(title = "My Leagues")

                Spacer(modifier = Modifier.height(14.dp))

                when {
                    leaguesState.isLoading -> {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    leaguesState.leagues.isEmpty() -> {
                        Text(
                            text = "You are not in any league yet.",
                            color = KickOffTheme.colors.muted,
                            fontSize = KickOffTypography.BodySize,
                            fontWeight = KickOffTypography.Bold
                        )
                    }

                    else -> {
                        leaguesState.leagues.take(3).forEach { league ->
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
        }
    }
}

@Composable
private fun SectionHeader(
    title: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = KickOffTypography.SectionTitleSize,
            fontWeight = KickOffTypography.ExtraBold,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun NextMatchCard(
    match: HomeNextMatchUi,
    onOpenClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(192.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.tertiary,
                        MaterialTheme.colorScheme.background
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(18.dp)
    ) {
        Column {
            Text(
                text = match.leagueName.uppercase(),
                color = MaterialTheme.colorScheme.primary,
                fontSize = KickOffTypography.LabelSize,
                fontWeight = KickOffTypography.ExtraBold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "${match.teamAName} vs ${match.teamBName}",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = KickOffTypography.SectionTitleSize,
                fontWeight = KickOffTypography.ExtraBold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                InfoChip(
                    icon = Icons.Default.CalendarMonth,
                    text = match.dateText
                )

                Spacer(modifier = Modifier.width(8.dp))

                InfoChip(
                    icon = Icons.Default.Schedule,
                    text = match.timeText
                )
            }

            if (match.location.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))

                InfoChip(
                    icon = Icons.Default.LocationOn,
                    text = match.location
                )
            }
        }

        Button(
            onClick = onOpenClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .height(34.dp),
            shape = RoundedCornerShape(11.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background
            )
        ) {
            Text(
                text = "Open",
                fontSize = KickOffTypography.LabelSize,
                fontWeight = KickOffTypography.ExtraBold
            )
        }
    }
}

@Composable
private fun InfoChip(
    icon: ImageVector,
    text: String
) {
    Row(
        modifier = Modifier
            .background(
                color = KickOffTheme.colors.input,
                shape = RoundedCornerShape(50.dp)
            )
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(14.dp)
        )

        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = KickOffTypography.LabelSize,
            fontWeight = KickOffTypography.Bold
        )
    }
}

@Composable
private fun LeagueCard(
    league: LeagueCardUi,
    onOpenClick: () -> Unit
) {
    val rankText = league.userRank?.let { "#$it" } ?: "-"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(132.dp)
            .background(
                color = KickOffTheme.colors.card,
                shape = RoundedCornerShape(18.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            text = league.leagueName,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = KickOffTypography.CardTitleSize,
            fontWeight = KickOffTypography.ExtraBold
        )

        Column(
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "$rankText   ⚡ ${league.userPoints} pts",
                color = KickOffTheme.colors.muted,
                fontSize = KickOffTypography.SmallSize,
                fontWeight = KickOffTypography.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Members",
                color = KickOffTheme.colors.muted,
                fontSize = KickOffTypography.LabelSize,
                fontWeight = KickOffTypography.ExtraBold
            )

            Text(
                text = "${league.memberCount}/${league.maxMembers}",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = KickOffTypography.SmallSize,
                fontWeight = KickOffTypography.Bold
            )
        }

        Button(
            onClick = onOpenClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .height(34.dp),
            shape = RoundedCornerShape(11.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background
            )
        ) {
            Text(
                text = "Open",
                fontSize = KickOffTypography.LabelSize,
                fontWeight = KickOffTypography.ExtraBold
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    KickOffTheme {
        HomeScreen(
            homeState = HomeUiState(),
            leaguesState = LeaguesUiState(),
            onOpenNextMatchClick = {},
            onOpenLeagueClick = {}
        )
    }
}