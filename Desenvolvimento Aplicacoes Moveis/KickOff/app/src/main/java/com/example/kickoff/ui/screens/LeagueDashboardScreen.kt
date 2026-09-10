package com.example.kickoff.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kickoff.model.LeagueDashboardUi
import com.example.kickoff.ui.components.TopBar
import com.example.kickoff.ui.components.TopBarStyle
import com.example.kickoff.ui.states.LeagueDashboardUiState
import com.example.kickoff.ui.theme.KickOffTheme
import com.example.kickoff.ui.theme.KickOffTypography

@Composable
fun LeagueDashboardScreen(
    state: LeagueDashboardUiState,
    onAdministrationClick: () -> Unit = {},
    onFantasyTeamClick: () -> Unit = {},
    onMatchesClick: () -> Unit = {},
    onRankingClick: () -> Unit = {},
    onPlayersClick: () -> Unit = {},
    onRulesClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val league = state.league

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar(
                title = "KickOff",
                style = TopBarStyle.Child,
                onBackClick = onBackClick
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 18.dp)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.errorMessage != null -> {
                    Text(
                        text = state.errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = KickOffTypography.BodySize,
                        fontWeight = KickOffTypography.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                league != null -> {
                    LeagueDashboardContent(
                        league = league,
                        onAdministrationClick = onAdministrationClick,
                        onFantasyTeamClick = onFantasyTeamClick,
                        onMatchesClick = onMatchesClick,
                        onRankingClick = onRankingClick,
                        onPlayersClick = onPlayersClick,
                        onRulesClick = onRulesClick
                    )
                }
            }
        }
    }
}

@Composable
private fun LeagueDashboardContent(
    league: LeagueDashboardUi,
    onAdministrationClick: () -> Unit,
    onFantasyTeamClick: () -> Unit,
    onMatchesClick: () -> Unit,
    onRankingClick: () -> Unit,
    onPlayersClick: () -> Unit,
    onRulesClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        LeagueHeaderCard(
            leagueName = league.name,
            creatorName = league.creatorName,
            memberCount = league.memberCount,
            maxMembers = league.maxMembers,
            inviteCode = league.inviteCode
        )

        Spacer(modifier = Modifier.height(18.dp))

        StatsGrid(
            currentWeek = league.currentWeek,
            memberCount = league.memberCount,
            maxMembers = league.maxMembers,
            userPoints = league.userPoints
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "League Management",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = KickOffTypography.BodySize,
            fontWeight = KickOffTypography.ExtraBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        ManagementGrid(
            onFantasyTeamClick = onFantasyTeamClick,
            onMatchesClick = onMatchesClick,
            onRankingClick = onRankingClick,
            onPlayersClick = onPlayersClick,
            onRulesClick = onRulesClick
        )

        Spacer(modifier = Modifier.height(18.dp))

        if (league.isCreator) {
            AdministrationCard(
                onClick = onAdministrationClick
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun LeagueHeaderCard(
    leagueName: String,
    creatorName: String,
    memberCount: Int,
    maxMembers: Int,
    inviteCode: String
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
            .padding(vertical = 22.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = leagueName,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = KickOffTypography.SectionTitleSize,
            fontWeight = KickOffTypography.ExtraBold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Creator: $creatorName",
            color = MaterialTheme.colorScheme.primary,
            fontSize = KickOffTypography.LabelSize,
            fontWeight = KickOffTypography.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "$memberCount/$maxMembers Members",
            color = MaterialTheme.colorScheme.primary,
            fontSize = KickOffTypography.LabelSize,
            fontWeight = KickOffTypography.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .background(
                    color = KickOffTheme.colors.input,
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Text(
                text = "CODE :  $inviteCode",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = KickOffTypography.SmallSize,
                fontWeight = KickOffTypography.ExtraBold
            )
        }
    }
}

@Composable
private fun StatsGrid(
    currentWeek: Int,
    memberCount: Int,
    maxMembers: Int,
    userPoints: Int
) {
    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard(
                title = "WEEK",
                value = currentWeek.toString(),
                modifier = Modifier.weight(1f)
            )

            StatCard(
                title = "MEMBERS",
                value = memberCount.toString(),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard(
                title = "LEAGUE SIZE",
                value = "$maxMembers Max",
                modifier = Modifier.weight(1f)
            )

            StatCard(
                title = "MY POINTS",
                value = userPoints.toString(),
                modifier = Modifier.weight(1f),
                valueColor = KickOffTheme.colors.yellow
            )
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    valueColor: Color = MaterialTheme.colorScheme.primary
) {
    Column(
        modifier = modifier
            .height(80.dp)
            .background(
                color = KickOffTheme.colors.card,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = KickOffTypography.LabelSize,
            fontWeight = KickOffTypography.ExtraBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = value,
            color = valueColor,
            fontSize = KickOffTypography.BodySize,
            fontWeight = KickOffTypography.ExtraBold
        )
    }
}

@Composable
private fun ManagementGrid(
    onFantasyTeamClick: () -> Unit,
    onMatchesClick: () -> Unit = {},
    onRankingClick: () -> Unit,
    onPlayersClick: () -> Unit,
    onRulesClick: () -> Unit
) {
    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ManagementCard(
                title = "Fantasy Team",
                subtitle = "Manage your\nactive roster",
                icon = Icons.Default.SportsSoccer,
                iconTint = MaterialTheme.colorScheme.primary,
                cardColor = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.weight(1f),
                onClick = onFantasyTeamClick
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ManagementCard(
                title = "Matches",
                subtitle = "Upcoming\nmatches and\npast results",
                icon = Icons.Default.CalendarMonth,
                iconTint = Color.Cyan,
                cardColor = KickOffTheme.colors.card,
                modifier = Modifier.weight(1f),
                onClick = onMatchesClick
            )

            ManagementCard(
                title = "Table",
                subtitle = "Check ranking",
                icon = Icons.Default.BarChart,
                iconTint = KickOffTheme.colors.yellow,
                cardColor = KickOffTheme.colors.card,
                modifier = Modifier.weight(1f),
                onClick = onRankingClick
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ManagementCard(
                title = "Players",
                subtitle = "Scout available\nplayers and stats",
                icon = Icons.Default.Groups,
                iconTint = Color.Magenta,
                cardColor = KickOffTheme.colors.card,
                modifier = Modifier.weight(1f),
                onClick = onPlayersClick
            )

            ManagementCard(
                title = "Rules",
                subtitle = "League scoring\nand participation\nguidelines.",
                icon = Icons.Default.Gavel,
                iconTint = KickOffTheme.colors.muted,
                cardColor = KickOffTheme.colors.card,
                modifier = Modifier.weight(1f),
                onClick = onRulesClick
            )
        }
    }
}

@Composable
private fun ManagementCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconTint: Color,
    cardColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .height(148.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        cardColor,
                        KickOffTheme.colors.card
                    )
                ),
                shape = RoundedCornerShape(14.dp)
            )
            .clickable { onClick() }
            .padding(14.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .background(
                    color = iconTint.copy(alpha = 0.18f),
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )
        }

        Column {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = KickOffTypography.BodySize,
                fontWeight = KickOffTypography.ExtraBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = subtitle,
                color = KickOffTheme.colors.muted,
                fontSize = KickOffTypography.LabelSize,
                lineHeight = 18.sp,
                fontWeight = KickOffTypography.Bold
            )
        }
    }
}

@Composable
private fun AdministrationCard(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
            .background(
                color = KickOffTheme.colors.card,
                shape = RoundedCornerShape(14.dp)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.45f),
                shape = RoundedCornerShape(14.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.18f),
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AdminPanelSettings,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Administration",
                color = MaterialTheme.colorScheme.primary,
                fontSize = KickOffTypography.BodySize,
                fontWeight = KickOffTypography.ExtraBold
            )

            Text(
                text = "Edit league settings, invite\nmembers, or moderate.",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = KickOffTypography.SmallSize,
                lineHeight = 13.sp,
                fontWeight = KickOffTypography.Bold
            )
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LeagueDashboardScreenPreview() {
    KickOffTheme {
        LeagueDashboardScreen(
            state = LeagueDashboardUiState(
                isLoading = false,
                league = LeagueDashboardUi(
                    leagueId = "1",
                    name = "The Elite Champions League",
                    creatorId = "creator_1",
                    creatorName = "Julian Draxler",
                    inviteCode = "ELITE-2024-X",
                    memberCount = 24,
                    maxMembers = 30,
                    currentWeek = 156,
                    userPoints = 84,
                    isCreator = true
                )
            )
        )
    }
}