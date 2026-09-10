package com.example.kickoff.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kickoff.model.MatchPlayerReportUi
import com.example.kickoff.ui.components.TopBar
import com.example.kickoff.ui.components.TopBarStyle
import com.example.kickoff.ui.states.MatchReportUiState
import com.example.kickoff.ui.theme.KickOffTheme
import com.example.kickoff.ui.theme.KickOffTypography

@Composable
fun MatchReportScreen(
    state: MatchReportUiState,
    onBackClick: () -> Unit = {},
    onTeamAScoreChange: (String) -> Unit = {},
    onTeamBScoreChange: (String) -> Unit = {},
    onPlayerTeamChange: (String, String?) -> Unit = { _, _ -> },
    onPlayerStatChange: (String, String, Int) -> Unit = { _, _, _ -> },
    onSubmitClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar(
                title = "Match Report",
                style = TopBarStyle.Child,
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            Button(
                onClick = onSubmitClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp, vertical = 12.dp)
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
                    Icon(Icons.Default.CheckCircle, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Submit Report",
                        fontSize = KickOffTypography.BodySize,
                        fontWeight = KickOffTypography.ExtraBold
                    )
                }
            }
        }
    ) { padding ->
        when {
            state.isLoading && state.players.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 18.dp)
                        .padding(bottom = 100.dp)
                ) {
                    ScoreCard(
                        teamAName = state.teamAName,
                        teamBName = state.teamBName,
                        teamAScore = state.teamAScore,
                        teamBScore = state.teamBScore,
                        onTeamAScoreChange = onTeamAScoreChange,
                        onTeamBScoreChange = onTeamBScoreChange
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    AttendanceSection(
                        title = state.teamAName,
                        team = "A",
                        players = state.players,
                        color = MaterialTheme.colorScheme.primary,
                        onPlayerTeamChange = onPlayerTeamChange
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    AttendanceSection(
                        title = state.teamBName,
                        team = "B",
                        players = state.players,
                        color = KickOffTheme.colors.yellow,
                        onPlayerTeamChange = onPlayerTeamChange
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    PlayerStatsSection(
                        players = state.players.filter { it.played },
                        onPlayerStatChange = onPlayerStatChange
                    )

                    state.errorMessage?.let {
                        Spacer(modifier = Modifier.height(16.dp))

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
    }
}

@Composable
private fun ScoreCard(
    teamAName: String,
    teamBName: String,
    teamAScore: String,
    teamBScore: String,
    onTeamAScoreChange: (String) -> Unit,
    onTeamBScoreChange: (String) -> Unit
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
            text = "SCORE",
            color = MaterialTheme.colorScheme.primary,
            fontSize = KickOffTypography.LabelSize,
            fontWeight = KickOffTypography.ExtraBold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ScoreInput(
                teamName = teamAName,
                value = teamAScore,
                onValueChange = onTeamAScoreChange,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "-",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 28.sp,
                fontWeight = KickOffTypography.ExtraBold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            ScoreInput(
                teamName = teamBName,
                value = teamBScore,
                onValueChange = onTeamBScoreChange,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ScoreInput(
    teamName: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = teamName,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = KickOffTypography.BodySize,
            fontWeight = KickOffTypography.ExtraBold,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it.filter { char -> char.isDigit() }) },
            modifier = Modifier
                .width(86.dp)
                .height(58.dp),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(
                fontSize = 20.sp,
                fontWeight = KickOffTypography.ExtraBold
            ),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = KickOffTheme.colors.input,
                unfocusedContainerColor = KickOffTheme.colors.input,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = KickOffTheme.colors.input,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Composable
private fun AttendanceSection(
    title: String,
    team: String,
    players: List<MatchPlayerReportUi>,
    color: Color,
    onPlayerTeamChange: (String, String?) -> Unit
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
            text = "$title Players (${players.count { it.team == team }}/7)",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = KickOffTypography.CardTitleSize,
            fontWeight = KickOffTypography.ExtraBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        players.forEach { player ->
            val selected = player.team == team
            val locked = player.team != null && player.team != team

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = selected,
                    enabled = !locked,
                    onCheckedChange = { checked ->
                        onPlayerTeamChange(
                            player.playerId,
                            if (checked) team else null
                        )
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = color,
                        uncheckedColor = KickOffTheme.colors.muted,
                        disabledCheckedColor = KickOffTheme.colors.muted,
                        disabledUncheckedColor = KickOffTheme.colors.muted.copy(alpha = 0.35f)
                    )
                )

                Text(
                    text = if (locked) "${player.name}  •  selected in other team" else player.name,
                    color = when {
                        selected -> color
                        locked -> KickOffTheme.colors.muted.copy(alpha = 0.45f)
                        else -> MaterialTheme.colorScheme.onSurface
                    },
                    fontSize = KickOffTypography.BodySize,
                    fontWeight = KickOffTypography.Bold
                )
            }
        }
    }
}

@Composable
private fun PlayerStatsSection(
    players: List<MatchPlayerReportUi>,
    onPlayerStatChange: (String, String, Int) -> Unit
) {
    Column {
        Text(
            text = "Player Stats",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = KickOffTypography.SectionTitleSize,
            fontWeight = KickOffTypography.ExtraBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (players.isEmpty()) {
            Text(
                text = "Select players first.",
                color = KickOffTheme.colors.muted,
                fontSize = KickOffTypography.BodySize,
                fontWeight = KickOffTypography.Bold
            )
        } else {
            players.forEach { player ->
                PlayerStatsCard(
                    player = player,
                    onPlayerStatChange = onPlayerStatChange
                )

                Spacer(modifier = Modifier.height(14.dp))
            }
        }
    }
}

@Composable
private fun PlayerStatsCard(
    player: MatchPlayerReportUi,
    onPlayerStatChange: (String, String, Int) -> Unit
) {
    val borderColor =
        if (player.team == "A") MaterialTheme.colorScheme.primary
        else KickOffTheme.colors.yellow

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = KickOffTheme.colors.card,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = borderColor.copy(alpha = 0.5f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            text = player.name,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = KickOffTypography.CardTitleSize,
            fontWeight = KickOffTypography.ExtraBold
        )

        Text(
            text = if (player.team == "A") "Team A" else "Team B",
            color = KickOffTheme.colors.muted,
            fontSize = KickOffTypography.SmallSize,
            fontWeight = KickOffTypography.Bold
        )

        Spacer(modifier = Modifier.height(14.dp))

        StatCounter("Goals", player.goals) {
            onPlayerStatChange(player.playerId, "goals", it)
        }

        StatCounter("Assists", player.assists) {
            onPlayerStatChange(player.playerId, "assists", it)
        }

        StatCounter("Saves", player.saves) {
            onPlayerStatChange(player.playerId, "saves", it)
        }

        StatCounter("Goals conceded", player.goalsConceded) {
            onPlayerStatChange(player.playerId, "goalsConceded", it)
        }

        StatCounter("Own goals", player.ownGoals) {
            onPlayerStatChange(player.playerId, "ownGoals", it)
        }

        StatCounter("Missed penalties", player.missedPenalties) {
            onPlayerStatChange(player.playerId, "missedPenalties", it)
        }

        StatCounter("Saved penalties", player.savedPenalties) {
            onPlayerStatChange(player.playerId, "savedPenalties", it)
        }
    }
}

@Composable
private fun StatCounter(
    label: String,
    value: Int,
    onChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = KickOffTheme.colors.muted,
            fontSize = KickOffTypography.SmallSize,
            fontWeight = KickOffTypography.Bold,
            modifier = Modifier.weight(1f)
        )

        IconButton(
            onClick = { onChange(-1) },
            modifier = Modifier.size(30.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = value.toString(),
            color = MaterialTheme.colorScheme.primary,
            fontSize = KickOffTypography.BodySize,
            fontWeight = KickOffTypography.ExtraBold,
            modifier = Modifier.width(28.dp),
            maxLines = 1
        )

        IconButton(
            onClick = { onChange(1) },
            modifier = Modifier.size(30.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MatchReportScreenPreview() {
    KickOffTheme {
        MatchReportScreen(
            state = MatchReportUiState(
                teamAName = "Claros",
                teamBName = "Escuros",
                teamAScore = "2",
                teamBScore = "1",
                players = listOf(
                    MatchPlayerReportUi(
                        playerId = "1",
                        name = "João Félix",
                        team = "A",
                        goals = 1
                    ),
                    MatchPlayerReportUi(
                        playerId = "2",
                        name = "Gonçalo Ramos",
                        team = "B",
                        assists = 1
                    ),
                    MatchPlayerReportUi(
                        playerId = "3",
                        name = "Rafael Leão"
                    )
                )
            )
        )
    }
}