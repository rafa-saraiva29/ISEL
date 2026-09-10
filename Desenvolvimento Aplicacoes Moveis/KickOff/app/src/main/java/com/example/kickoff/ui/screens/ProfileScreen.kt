package com.example.kickoff.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kickoff.model.ProfileUi
import com.example.kickoff.ui.components.TopBar
import com.example.kickoff.ui.components.TopBarStyle
import com.example.kickoff.ui.states.ProfileUiState
import com.example.kickoff.ui.theme.KickOffTheme
import com.example.kickoff.ui.theme.KickOffTypography

@Composable
fun ProfileScreen(
    state: ProfileUiState,
    onLogoutClick: () -> Unit = {}
) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = {
                showLogoutDialog = false
            },
            containerColor = KickOffTheme.colors.card,
            title = {
                Text(
                    text = "Log out",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = KickOffTypography.ExtraBold
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to log out of KickOff?",
                    color = KickOffTheme.colors.muted,
                    fontSize = KickOffTypography.BodySize,
                    fontWeight = KickOffTypography.Bold
                )
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                    }
                ) {
                    Text(
                        text = "Cancel",
                        color = KickOffTheme.colors.muted,
                        fontWeight = KickOffTypography.Bold
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        onLogoutClick()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Text(
                        text = "Logout",
                        fontWeight = KickOffTypography.ExtraBold
                    )
                }
            }
        )
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar(
                title = "Profile",
                style = TopBarStyle.Root
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
                            .height(220.dp),
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

                state.profile != null -> {
                    ProfileContent(
                        profile = state.profile,
                        onLogoutClick = {
                            showLogoutDialog = true
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileContent(
    profile: ProfileUi,
    onLogoutClick: () -> Unit
) {
    Column {
        ProfileHeader(profile)

        Spacer(modifier = Modifier.height(22.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ProfileStatCard(
                title = "GLOBAL RANK",
                value = profile.globalRank?.let { "#$it" } ?: "-",
                modifier = Modifier.weight(1f),
                highlight = true
            )

            ProfileStatCard(
                title = "POINTS",
                value = profile.globalPoints.toString(),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ProfileStatCard(
                title = "LEAGUES",
                value = profile.leaguesCount.toString(),
                modifier = Modifier.weight(1f)
            )

            ProfileStatCard(
                title = "AVG PTS",
                value = String.format("%.1f", profile.averagePoints),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = onLogoutClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(26.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Logout,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Logout",
                fontSize = KickOffTypography.BodySize,
                fontWeight = KickOffTypography.ExtraBold
            )
        }
    }
}

@Composable
private fun ProfileHeader(
    profile: ProfileUi
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
                shape = RoundedCornerShape(20.dp)
            )
            .padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(82.dp)
                .background(
                    color = KickOffTheme.colors.input,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(44.dp)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = profile.fullName,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 22.dp.value.sp,
            fontWeight = KickOffTypography.ExtraBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null,
                tint = KickOffTheme.colors.muted,
                modifier = Modifier.size(15.dp)
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = profile.email,
                color = KickOffTheme.colors.muted,
                fontSize = KickOffTypography.SmallSize,
                fontWeight = KickOffTypography.Bold
            )
        }
    }
}

@Composable
private fun ProfileStatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    highlight: Boolean = false
) {
    val valueColor =
        if (highlight) KickOffTheme.colors.yellow
        else MaterialTheme.colorScheme.primary

    val icon = when (title) {
        "GLOBAL RANK" -> Icons.Default.EmojiEvents
        "POINTS" -> Icons.Default.Star
        "LEAGUES" -> Icons.Default.Groups
        "AVG PTS" -> Icons.AutoMirrored.Filled.TrendingUp
        else -> Icons.Default.Person
    }

    Column(
        modifier = modifier
            .height(112.dp)
            .background(
                color = KickOffTheme.colors.card,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(14.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = valueColor,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = value,
            color = valueColor,
            fontSize = 22.dp.value.sp,
            fontWeight = KickOffTypography.ExtraBold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = title,
            color = KickOffTheme.colors.muted,
            fontSize = KickOffTypography.LabelSize,
            fontWeight = KickOffTypography.ExtraBold
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    KickOffTheme {
        ProfileScreen(
            state = ProfileUiState(
                profile = ProfileUi(
                    userId = "1",
                    fullName = "Rafael Saraiva",
                    email = "rafa@email.com",
                    globalPoints = 2482,
                    globalRank = 4,
                    leaguesCount = 3,
                    matchesPlayed = 12,
                    averagePoints = 18.7
                )
            )
        )
    }
}