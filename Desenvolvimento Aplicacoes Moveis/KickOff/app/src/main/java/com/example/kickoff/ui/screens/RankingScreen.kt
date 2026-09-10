package com.example.kickoff.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.kickoff.model.RankingMemberUi
import com.example.kickoff.ui.components.TopBar
import com.example.kickoff.ui.components.TopBarStyle
import com.example.kickoff.ui.states.RankingUiState
import com.example.kickoff.ui.theme.KickOffTheme
import com.example.kickoff.ui.theme.KickOffTypography

@Composable
fun RankingScreen(
    state: RankingUiState,
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar(
                title = "Table",
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
                    text = "League ranking by total points",
                    color = KickOffTheme.colors.muted,
                    fontSize = KickOffTypography.BodySize,
                    fontWeight = KickOffTypography.Bold
                )

                Spacer(modifier = Modifier.height(22.dp))
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

                state.members.isEmpty() -> {
                    item {
                        Text(
                            text = "No ranking data yet.",
                            color = KickOffTheme.colors.muted,
                            fontSize = KickOffTypography.BodySize,
                            fontWeight = KickOffTypography.Bold
                        )
                    }
                }

                else -> {
                    items(state.members) { member ->
                        RankingCard(member = member)
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun RankingCard(
    member: RankingMemberUi
) {
    val rankColor = when (member.rank) {
        1 -> KickOffTheme.colors.yellow
        2 -> Color(0xFFC0C0C0)
        3 -> Color(0xFFCD7F32)
        else -> KickOffTheme.colors.muted
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = KickOffTheme.colors.card,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(
                    color = rankColor.copy(alpha = 0.18f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (member.rank <= 3) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = null,
                    tint = rankColor,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(
                    text = "#${member.rank}",
                    color = rankColor,
                    fontSize = KickOffTypography.BodySize,
                    fontWeight = KickOffTypography.ExtraBold
                )
            }
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = member.name,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = KickOffTypography.CardTitleSize,
                fontWeight = KickOffTypography.ExtraBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Rank #${member.rank}",
                color = KickOffTheme.colors.muted,
                fontSize = KickOffTypography.BodySize,
                fontWeight = KickOffTypography.Bold
            )
        }

        Text(
            text = "${member.points} pts",
            color = MaterialTheme.colorScheme.primary,
            fontSize = KickOffTypography.SectionTitleSize,
            fontWeight = KickOffTypography.ExtraBold
        )
    }
}