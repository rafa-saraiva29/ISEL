package com.example.kickoff.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kickoff.model.FantasyPlayerUi
import com.example.kickoff.ui.components.TopBar
import com.example.kickoff.ui.components.TopBarStyle
import com.example.kickoff.ui.states.FantasyTeamUiState
import com.example.kickoff.ui.theme.KickOffTheme
import com.example.kickoff.ui.theme.KickOffTypography

@Composable
fun FantasyTeamScreen(
    state: FantasyTeamUiState,
    onBackClick: () -> Unit = {},
    onSlotClick: (Int) -> Unit = {},
    onBenchPlayerClick: (FantasyPlayerUi) -> Unit = {},
    onSaveTeamClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar(
                title = "Fantasy Team",
                style = TopBarStyle.Child,
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            Button(
                onClick = onSaveTeamClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 68.dp, vertical = 18.dp)
                    .height(52.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.background
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Save Team",
                    fontWeight = KickOffTypography.ExtraBold
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(bottom = 10.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                InfoCard(
                    title = "TOTAL POINTS",
                    value = state.totalPoints.toString(),
                    modifier = Modifier.weight(1f)
                )

                InfoCard(
                    title = "TRANSFERS",
                    value = state.transfersLeft.toString(),
                    modifier = Modifier.weight(1f),
                    highlight = true
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            PitchCard(
                players = state.startingSeven,
                selectedSlotIndex = state.selectedSlotIndex,
                onSlotClick = onSlotClick
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Bench",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = KickOffTypography.CardTitleSize,
                fontWeight = KickOffTypography.ExtraBold
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                state.bench.forEach { player ->
                    BenchPlayerItem(
                        player = player,
                        onClick = {
                            onBenchPlayerClick(player)
                        }
                    )
                }
            }

            state.errorMessage?.let {
                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = KickOffTypography.SmallSize,
                    fontWeight = KickOffTypography.Bold
                )
            }
        }
    }
}

@Composable
private fun InfoCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    highlight: Boolean = false
) {
    val borderColor =
        if (highlight) KickOffTheme.colors.yellow
        else MaterialTheme.colorScheme.primary

    val valueColor =
        if (highlight) KickOffTheme.colors.yellow
        else MaterialTheme.colorScheme.primary

    Column(
        modifier = modifier
            .height(82.dp)
            .background(
                color = KickOffTheme.colors.card,
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            color = KickOffTheme.colors.muted,
            fontSize = KickOffTypography.BodySize,
            fontWeight = KickOffTypography.ExtraBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = value,
            color = valueColor,
            fontSize = 24.sp,
            fontWeight = KickOffTypography.ExtraBold
        )
    }
}

@Composable
private fun PitchCard(
    players: List<FantasyPlayerUi?>,
    selectedSlotIndex: Int?,
    onSlotClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(470.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.22f),
                        KickOffTheme.colors.card
                    )
                )
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.25f),
                shape = RoundedCornerShape(18.dp)
            )
    ) {
        PlayerSlot(
            player = players.getOrNull(0),
            selected = selectedSlotIndex == 0,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 28.dp),
            onClick = { onSlotClick(0) }
        )

        PlayerSlot(
            player = players.getOrNull(1),
            selected = selectedSlotIndex == 1,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 48.dp, top = 100.dp),
            onClick = { onSlotClick(1) }
        )

        PlayerSlot(
            player = players.getOrNull(2),
            selected = selectedSlotIndex == 2,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 160.dp),
            onClick = { onSlotClick(2) }
        )

        PlayerSlot(
            player = players.getOrNull(3),
            selected = selectedSlotIndex == 3,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 48.dp, top = 100.dp),
            onClick = { onSlotClick(3) }
        )

        PlayerSlot(
            player = players.getOrNull(4),
            selected = selectedSlotIndex == 4,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 50.dp, top = 245.dp),
            onClick = { onSlotClick(4) }
        )

        PlayerSlot(
            player = players.getOrNull(5),
            selected = selectedSlotIndex == 5,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 50.dp, top = 245.dp),
            onClick = { onSlotClick(5) }
        )

        PlayerSlot(
            player = players.getOrNull(6),
            selected = selectedSlotIndex == 6,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            onClick = { onSlotClick(6) }
        )
    }
}

@Composable
private fun PlayerSlot(
    player: FantasyPlayerUi?,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val slotColor = when {
        selected -> KickOffTheme.colors.yellow
        player != null -> MaterialTheme.colorScheme.primary
        else -> KickOffTheme.colors.muted
    }

    Column(
        modifier = modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .border(
                    width = 2.dp,
                    color = slotColor.copy(alpha = 0.75f),
                    shape = CircleShape
                )
                .background(slotColor.copy(alpha = 0.16f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = player?.name?.take(1)?.uppercase() ?: "+",
                color = slotColor,
                fontSize = 20.sp,
                fontWeight = KickOffTypography.ExtraBold
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .background(
                    color = KickOffTheme.colors.card.copy(alpha = 0.85f),
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(
                text = player?.name ?: "Select",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = KickOffTypography.SmallSize,
                fontWeight = KickOffTypography.ExtraBold,
                maxLines = 1
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = player?.points?.toString() ?: "-",
            color = slotColor,
            fontSize = KickOffTypography.BodySize,
            fontWeight = KickOffTypography.Bold
        )
    }
}

@Composable
private fun BenchPlayerItem(
    player: FantasyPlayerUi,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(86.dp)
            .height(132.dp)
            .background(
                color = KickOffTheme.colors.card,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .background(
                    color = KickOffTheme.colors.input,
                    shape = CircleShape
                )
                .border(
                    width = 1.dp,
                    color = KickOffTheme.colors.muted.copy(alpha = 0.45f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = player.name.take(1).uppercase(),
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = KickOffTypography.CardTitleSize,
                fontWeight = KickOffTypography.ExtraBold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = player.name,
            color = MaterialTheme.colorScheme.primary,
            fontSize = KickOffTypography.LabelSize,
            fontWeight = KickOffTypography.Bold,
            maxLines = 2,
            lineHeight = 14.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "${player.points} pts",
            color = KickOffTheme.colors.muted,
            fontSize = KickOffTypography.LabelSize,
            fontWeight = KickOffTypography.Bold
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FantasyTeamScreenPreview() {
    KickOffTheme {
        FantasyTeamScreen(
            state = FantasyTeamUiState(
                totalPoints = 57,
                transfersLeft = 2,
                startingSeven = List(7) { null },
                bench = listOf(
                    FantasyPlayerUi("1", "Areola", 10),
                    FantasyPlayerUi("2", "Konsa", 8),
                    FantasyPlayerUi("3", "Rogers", 15),
                    FantasyPlayerUi("4", "Solanke", 24)
                )
            )
        )
    }
}