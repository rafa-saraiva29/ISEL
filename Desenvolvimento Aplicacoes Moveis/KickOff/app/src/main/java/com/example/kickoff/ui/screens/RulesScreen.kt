package com.example.kickoff.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kickoff.ui.components.TopBar
import com.example.kickoff.ui.components.TopBarStyle
import com.example.kickoff.ui.theme.KickOffTheme
import com.example.kickoff.ui.theme.KickOffTypography

@Composable
fun RulesScreen(
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar(
                title = "Rules",
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
                    text = "Scoring Rules",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = KickOffTypography.ScreenTitleSize,
                    fontWeight = KickOffTypography.ExtraBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Fantasy points are calculated based on player performance in each match.",
                    color = KickOffTheme.colors.muted,
                    fontSize = KickOffTypography.BodySize,
                    fontWeight = KickOffTypography.Bold
                )

                Spacer(modifier = Modifier.height(22.dp))

                RuleSection(
                    title = "Positive Points",
                    rules = listOf(
                        RuleItem("Played match", "+1 pt"),
                        RuleItem("Goal", "+5 pts"),
                        RuleItem("Assist", "+3 pts"),
                        RuleItem("Save", "+1 pt"),
                        RuleItem("Penalty saved", "+5 pts")
                    )
                )

                Spacer(modifier = Modifier.height(18.dp))

                RuleSection(
                    title = "Negative Points",
                    rules = listOf(
                        RuleItem("Own goal", "-2 pts"),
                        RuleItem("Penalty missed", "-3 pts"),
                        RuleItem("Goal conceded", "-1 pt")
                    )
                )

                Spacer(modifier = Modifier.height(18.dp))

                InfoCard()
            }
        }
    }
}

private data class RuleItem(
    val label: String,
    val points: String
)

@Composable
private fun RuleSection(
    title: String,
    rules: List<RuleItem>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = KickOffTheme.colors.card,
                shape = RoundedCornerShape(18.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = KickOffTypography.SectionTitleSize,
            fontWeight = KickOffTypography.ExtraBold
        )

        Spacer(modifier = Modifier.height(14.dp))

        rules.forEach { rule ->
            RuleRow(rule)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun RuleRow(
    rule: RuleItem
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = KickOffTheme.colors.input,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.16f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.SportsSoccer,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = rule.label,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = KickOffTypography.BodySize,
            fontWeight = KickOffTypography.Bold,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = rule.points,
            color = if (rule.points.startsWith("+")) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.error
            },
            fontSize = KickOffTypography.CardTitleSize,
            fontWeight = KickOffTypography.ExtraBold
        )
    }
}

@Composable
private fun InfoCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = KickOffTheme.colors.card,
                shape = RoundedCornerShape(18.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Gavel,
            contentDescription = null,
            tint = KickOffTheme.colors.yellow,
            modifier = Modifier.size(28.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "Only selected players in the user fantasy team earn points for that match.",
            color = KickOffTheme.colors.muted,
            fontSize = KickOffTypography.BodySize,
            fontWeight = KickOffTypography.Bold
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RulesScreenPreview() {
    KickOffTheme {
        RulesScreen()
    }
}