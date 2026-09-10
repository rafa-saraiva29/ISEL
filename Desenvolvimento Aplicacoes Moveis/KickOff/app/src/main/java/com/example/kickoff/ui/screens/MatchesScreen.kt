package com.example.kickoff.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kickoff.model.Match
import com.example.kickoff.ui.components.FabAction
import com.example.kickoff.ui.components.ExpandableFab
import com.example.kickoff.ui.components.TopBar
import com.example.kickoff.ui.components.TopBarStyle
import com.example.kickoff.ui.states.MatchesUiState
import com.example.kickoff.ui.theme.KickOffTheme
import com.example.kickoff.ui.theme.KickOffTypography

@Composable
fun MatchesScreen(
    state: MatchesUiState,
    onBackClick: () -> Unit = {},
    onCreateMatchClick: () -> Unit = {},
    onMatchReportClick: (Match) -> Unit = {},
    onViewLineupClick: (Match) -> Unit = {}
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar(
                title = "Matches",
                style = TopBarStyle.Child,
                onBackClick = onBackClick
            )
        },
        floatingActionButton = {
            if (state.isCreator) {
                ExpandableFab(
                    actions = listOf(
                        FabAction(
                            text = "Create Match",
                            icon = Icons.Default.CalendarMonth,
                            onClick = onCreateMatchClick
                        )
                    )
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 18.dp),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            when {
                state.isLoading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                state.errorMessage != null -> {
                    item {
                        Text(
                            text = state.errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = KickOffTypography.BodySize,
                            fontWeight = KickOffTypography.Bold
                        )
                    }
                }

                else -> {
                    state.nextMatch?.let { match ->
                        item {
                            WeekHeader(
                                title = "Next Match",
                                week = state.currentWeek,
                                dateText = match.dateText
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            MatchCard(
                                match = match,
                                isCreator = state.isCreator,
                                onMatchReportClick = {
                                    onMatchReportClick(match)
                                },
                                onViewLineupClick = {
                                    onViewLineupClick(match)
                                }
                            )

                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }

                    if (state.upcomingMatches.isNotEmpty()) {
                        item {
                            Text(
                                text = "Upcoming Matches",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = KickOffTypography.SectionTitleSize,
                                fontWeight = KickOffTypography.ExtraBold
                            )

                            Spacer(modifier = Modifier.height(14.dp))
                        }

                        items(state.upcomingMatches) { match ->
                            MatchCard(
                                match = match,
                                isCreator = state.isCreator,
                                onMatchReportClick = {
                                    onMatchReportClick(match)
                                },
                                onViewLineupClick = {
                                    onViewLineupClick(match)
                                }
                            )

                            Spacer(modifier = Modifier.height(14.dp))
                        }

                        item {
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }

                    if (state.pastMatches.isNotEmpty()) {
                        item {
                            Text(
                                text = "Past Matches",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = KickOffTypography.SectionTitleSize,
                                fontWeight = KickOffTypography.ExtraBold
                            )

                            Spacer(modifier = Modifier.height(14.dp))
                        }

                        items(state.pastMatches) { match ->
                            MatchCard(
                                match = match,
                                isCreator = state.isCreator,
                                onMatchReportClick = {
                                    onMatchReportClick(match)
                                },
                                onViewLineupClick = {
                                    onViewLineupClick(match)
                                }
                            )

                            Spacer(modifier = Modifier.height(14.dp))
                        }
                    }

                    if (
                        state.nextMatch == null &&
                        state.upcomingMatches.isEmpty() &&
                        state.pastMatches.isEmpty()
                    ) {
                        item {
                            EmptyMatchesCard()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WeekHeader(
    title: String,
    week: Int,
    dateText: String
) {
    Column {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = KickOffTypography.SectionTitleSize,
            fontWeight = KickOffTypography.ExtraBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "WEEK $week",
            color = MaterialTheme.colorScheme.primary,
            fontSize = KickOffTypography.LabelSize,
            fontWeight = KickOffTypography.ExtraBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(15.dp)
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = dateText,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = KickOffTypography.BodySize,
                fontWeight = KickOffTypography.Medium
            )
        }
    }
}

@Composable
private fun MatchCard(
    match: Match,
    isCreator: Boolean,
    onMatchReportClick: () -> Unit,
    onViewLineupClick: () -> Unit
) {
    val canSetReport =
        isCreator &&
                match.status == "scheduled" &&
                match.scheduledMillis <= System.currentTimeMillis()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.tertiary,
                        MaterialTheme.colorScheme.background
                    )
                ),
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                width = 1.dp,
                color = KickOffTheme.colors.input,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (match.isFinished) match.dateText else match.timeText,
                color = KickOffTheme.colors.muted,
                fontSize = KickOffTypography.LabelSize,
                fontWeight = KickOffTypography.ExtraBold
            )

            Spacer(modifier = Modifier.weight(1f))

            if (match.isFinished) {
                Surface(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "+${match.userPoints} PTS",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = KickOffTypography.LabelSize,
                        fontWeight = KickOffTypography.ExtraBold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))



        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TeamBlock(
                teamName = match.teamAName,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f)
            )

            if (match.isFinished) {
                Text(
                    text = "${match.teamAScore ?: 0}  -  ${match.teamBScore ?: 0}",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = KickOffTypography.ScreenTitleSize,
                    fontWeight = FontWeight.Black
                )
            }

            TeamBlock(
                teamName = match.teamBName,
                color = KickOffTheme.colors.yellow,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = if (canSetReport) onMatchReportClick else onViewLineupClick,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(34.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor =
                    if (canSetReport) MaterialTheme.colorScheme.primary
                    else KickOffTheme.colors.input,
                contentColor =
                    if (canSetReport) MaterialTheme.colorScheme.background
                    else MaterialTheme.colorScheme.onSurface
            ),
            contentPadding = PaddingValues(horizontal = 18.dp)
        ) {
            Text(
                text = if (canSetReport) "Match Report" else "View Fantasy Team",
                fontSize = KickOffTypography.CardTitleSize,
                fontWeight = KickOffTypography.ExtraBold
            )
        }
    }
}

@Composable
private fun TeamBlock(
    teamName: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(
                    color = KickOffTheme.colors.input,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.SportsSoccer,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(26.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = teamName,
            color = KickOffTheme.colors.muted,
            fontSize = KickOffTypography.BodySize,
            fontWeight = KickOffTypography.ExtraBold,
            maxLines = 1
        )
    }
}

@Composable
private fun EmptyMatchesCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = KickOffTheme.colors.card,
                shape = RoundedCornerShape(14.dp)
            )
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No matches yet",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = KickOffTypography.CardTitleSize,
            fontWeight = KickOffTypography.ExtraBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Create the first match for this league.",
            color = KickOffTheme.colors.muted,
            fontSize = KickOffTypography.BodySize
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MatchesScreenPreview() {
    KickOffTheme {
        MatchesScreen(
            state = MatchesUiState(
                isCreator = true,

                nextMatch = Match(
                    matchId = "1",
                    week = 24,
                    dateText = "Saturday, 12 Oct 2026",
                    timeText = "SAT, 12 OCT, 20:00",
                    teamAName = "Claros",
                    teamBName = "Escuros",
                    status = "scheduled",
                    scheduledMillis = System.currentTimeMillis() + 86_400_000
                ),

                pastMatches = listOf(
                    Match(
                        matchId = "3",
                        week = 23,
                        dateText = "Friday, 10 Oct 2026",
                        timeText = "FRI, 10 OCT, 19:30",
                        teamAName = "Team A",
                        teamBName = "Team B",
                        status = "finished",
                        scheduledMillis = System.currentTimeMillis() - 86_400_000,
                        teamAScore = 5,
                        teamBScore = 2,
                        userPoints = 18
                    )
                )
            )
        )
    }
}