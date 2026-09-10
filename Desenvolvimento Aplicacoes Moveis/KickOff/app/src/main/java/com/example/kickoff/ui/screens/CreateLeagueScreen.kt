package com.example.kickoff.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kickoff.ui.components.TopBar
import com.example.kickoff.ui.components.TopBarStyle
import com.example.kickoff.ui.theme.KickOffTheme
import com.example.kickoff.ui.theme.KickOffTypography

@Composable
fun CreateLeagueScreen(
    onBackClick: () -> Unit = {},
    onCreateLeagueClick: (
        leagueName: String,
        maxMembers: Int,
        inviteCode: String
    ) -> Unit = { _, _, _ -> }
) {
    var leagueName by remember { mutableStateOf("") }
    var inviteCode by remember { mutableStateOf("") }
    var maxMembers by remember { mutableFloatStateOf(12f) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar(
                title = "Create League",
                style = TopBarStyle.Child,
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    onCreateLeagueClick(
                        leagueName,
                        maxMembers.toInt(),
                        inviteCode
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 16.dp)
                    .height(54.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.background
                )
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Create League",
                    fontSize = KickOffTypography.BodySize,
                    fontWeight = KickOffTypography.ExtraBold
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 18.dp)
        ) {
            CreateInputCard(label = "LEAGUE NAME") {
                DarkTextField(
                    value = leagueName,
                    onValueChange = { leagueName = it },
                    placeholder = "e.g. Peladinhas 2026"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            MaxMembersCard(
                value = maxMembers,
                onValueChange = { maxMembers = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            CreateInputCard(label = "INVITE CODE") {
                DarkTextField(
                    value = inviteCode,
                    onValueChange = { inviteCode = it },
                    placeholder = "e.g. KICKOFF2026"
                )
            }
        }
    }
}

@Composable
private fun CreateInputCard(
    label: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = KickOffTheme.colors.card,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(14.dp)
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.primary,
            fontSize = KickOffTypography.LabelSize,
            fontWeight = KickOffTypography.ExtraBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        content()
    }
}

@Composable
private fun DarkTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
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
                text = placeholder,
                color = KickOffTheme.colors.muted,
                fontSize = KickOffTypography.BodySize,
                fontWeight = KickOffTypography.Bold
            )
        },
        shape = RoundedCornerShape(8.dp),
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

@Composable
private fun MaxMembersCard(
    value: Float,
    onValueChange: (Float) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = KickOffTheme.colors.card,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(14.dp)
    ) {
        Text(
            text = "MAX MEMBERS",
            color = MaterialTheme.colorScheme.primary,
            fontSize = KickOffTypography.LabelSize,
            fontWeight = KickOffTypography.ExtraBold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Groups,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(18.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = value.toInt().toString(),
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = KickOffTypography.CardTitleSize,
                fontWeight = KickOffTypography.ExtraBold
            )

            Spacer(modifier = Modifier.width(28.dp))

            Slider(
                value = value,
                onValueChange = onValueChange,
                valueRange = 2f..30f,
                steps = 28,
                modifier = Modifier.weight(1f),
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    activeTrackColor = MaterialTheme.colorScheme.primary,
                    inactiveTrackColor = KickOffTheme.colors.muted
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateLeagueScreenPreview() {
    KickOffTheme {
        CreateLeagueScreen()
    }
}