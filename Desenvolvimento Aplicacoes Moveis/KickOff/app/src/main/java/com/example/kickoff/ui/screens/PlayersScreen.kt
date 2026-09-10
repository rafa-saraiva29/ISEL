package com.example.kickoff.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kickoff.model.FantasyPlayer
import com.example.kickoff.ui.components.TopBar
import com.example.kickoff.ui.components.TopBarStyle
import com.example.kickoff.ui.states.PlayersUiState
import com.example.kickoff.ui.theme.KickOffTheme
import com.example.kickoff.ui.theme.KickOffTypography

private enum class PlayerSortOption(
    val label: String
) {
    Points("Points"),
    Goals("Goals"),
    Assists("Assists"),
    Saves("Saves")
}

@Composable
fun PlayersScreen(
    state: PlayersUiState,
    onBackClick: () -> Unit = {}
) {
    var searchText by remember { mutableStateOf("") }
    var sortOption by remember { mutableStateOf(PlayerSortOption.Points) }

    val filteredPlayers = remember(
        state.players,
        searchText,
        sortOption
    ) {
        state.players
            .filter {
                it.name.contains(searchText, ignoreCase = true)
            }
            .let { players ->
                when (sortOption) {
                    PlayerSortOption.Points -> players.sortedByDescending { it.points }
                    PlayerSortOption.Goals -> players.sortedByDescending { it.goals }
                    PlayerSortOption.Assists -> players.sortedByDescending { it.assists }
                    PlayerSortOption.Saves -> players.sortedByDescending { it.saves }
                }
            }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar(
                title = "Players",
                style = TopBarStyle.Child,
                onBackClick = onBackClick
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
                Text(
                    text = "Scout all fantasy players in this league.",
                    color = KickOffTheme.colors.muted,
                    fontSize = KickOffTypography.BodySize,
                    fontWeight = KickOffTypography.Bold
                )

                Spacer(modifier = Modifier.height(18.dp))

                SearchField(
                    value = searchText,
                    onValueChange = { searchText = it }
                )

                Spacer(modifier = Modifier.height(14.dp))

                SortSelector(
                    selected = sortOption,
                    onSelected = { sortOption = it }
                )

                Spacer(modifier = Modifier.height(18.dp))
            }

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

                filteredPlayers.isEmpty() -> {
                    item {
                        EmptyPlayersCard()
                    }
                }

                else -> {
                    items(filteredPlayers) { player ->
                        val rank = state.players
                            .sortedByDescending { it.points }
                            .indexOfFirst { it.playerId == player.playerId } + 1

                        ExpandablePlayerCard(
                            player = player,
                            rank = rank
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchField(
    value: String,
    onValueChange: (String) -> Unit
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
                text = "Search player...",
                color = KickOffTheme.colors.muted,
                fontSize = KickOffTypography.BodySize,
                fontWeight = KickOffTypography.Bold
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        shape = RoundedCornerShape(14.dp),
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

@Composable
private fun SortSelector(
    selected: PlayerSortOption,
    onSelected: (PlayerSortOption) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PlayerSortOption.entries.forEach { option ->
            val isSelected = selected == option

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        onSelected(option)
                    },
                shape = RoundedCornerShape(50.dp),
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
                } else {
                    KickOffTheme.colors.card
                }
            ) {
                Text(
                    text = option.label,
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        KickOffTheme.colors.muted
                    },
                    fontSize = KickOffTypography.SmallSize,
                    fontWeight = KickOffTypography.ExtraBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(
                        horizontal = 8.dp,
                        vertical = 9.dp
                    )
                )
            }
        }
    }
}

@Composable
private fun ExpandablePlayerCard(
    player: FantasyPlayer,
    rank: Int
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .background(
                color = KickOffTheme.colors.card,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable {
                expanded = !expanded
            }
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RankIcon(rank = rank)

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = player.name,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = KickOffTypography.CardTitleSize,
                    fontWeight = KickOffTypography.ExtraBold
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "${player.goals} G   ${player.assists} A   ${player.played} GP",
                    color = KickOffTheme.colors.muted,
                    fontSize = KickOffTypography.SmallSize,
                    fontWeight = KickOffTypography.Bold
                )
            }

            Text(
                text = "${player.points} pts",
                color = MaterialTheme.colorScheme.primary,
                fontSize = KickOffTypography.CardTitleSize,
                fontWeight = KickOffTypography.ExtraBold
            )
        }

        if (expanded) {
            Spacer(modifier = Modifier.height(18.dp))

            HorizontalDivider(
                Modifier,
                DividerDefaults.Thickness,
                color = KickOffTheme.colors.input
            )

            Spacer(modifier = Modifier.height(16.dp))

            StatsGrid(player)
        }
    }
}

@Composable
private fun RankIcon(
    rank: Int
) {
    val rankColor = when (rank) {
        1 -> KickOffTheme.colors.yellow
        2 -> KickOffTheme.colors.muted
        3 -> MaterialTheme.colorScheme.primary.copy(alpha = 0.75f)
        else -> MaterialTheme.colorScheme.primary
    }

    Box(
        modifier = Modifier
            .size(46.dp)
            .background(
                color = rankColor.copy(alpha = 0.16f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        if (rank in 1..3) {
            Icon(
                imageVector = Icons.Default.EmojiEvents,
                contentDescription = null,
                tint = rankColor,
                modifier = Modifier.size(24.dp)
            )
        } else {
            Icon(
                imageVector = Icons.Default.SportsSoccer,
                contentDescription = null,
                tint = rankColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun StatsGrid(
    player: FantasyPlayer
) {
    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            StatBox(
                title = "PLAYED",
                value = player.played.toString(),
                modifier = Modifier.weight(1f)
            )

            StatBox(
                title = "GOALS",
                value = player.goals.toString(),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            StatBox(
                title = "ASSISTS",
                value = player.assists.toString(),
                modifier = Modifier.weight(1f)
            )

            StatBox(
                title = "SAVES",
                value = player.saves.toString(),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            StatBox(
                title = "CONCEDED",
                value = player.goalsConceded.toString(),
                modifier = Modifier.weight(1f)
            )

            StatBox(
                title = "OWN GOALS",
                value = player.ownGoals.toString(),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            StatBox(
                title = "MISSED PENS",
                value = player.missedPenalties.toString(),
                modifier = Modifier.weight(1f)
            )

            StatBox(
                title = "SAVED PENS",
                value = player.savedPenalties.toString(),
                modifier = Modifier.weight(1f)
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
            .height(64.dp)
            .background(
                color = KickOffTheme.colors.input,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            color = KickOffTheme.colors.muted,
            fontSize = KickOffTypography.LabelSize,
            fontWeight = KickOffTypography.ExtraBold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = value,
            color = MaterialTheme.colorScheme.primary,
            fontSize = KickOffTypography.CardTitleSize,
            fontWeight = KickOffTypography.ExtraBold
        )
    }
}

@Composable
private fun EmptyPlayersCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = KickOffTheme.colors.card,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.PersonSearch,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(34.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "No players found",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = KickOffTypography.CardTitleSize,
            fontWeight = KickOffTypography.ExtraBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Add fantasy players in the administration screen.",
            color = KickOffTheme.colors.muted,
            fontSize = KickOffTypography.BodySize,
            fontWeight = KickOffTypography.Bold,
            textAlign = TextAlign.Center
        )
    }
}