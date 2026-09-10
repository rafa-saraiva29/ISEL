@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.kickoff.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kickoff.ui.components.TopBar
import com.example.kickoff.ui.components.TopBarStyle
import com.example.kickoff.ui.states.CreateMatchUiState
import com.example.kickoff.ui.theme.KickOffTheme
import com.example.kickoff.ui.theme.KickOffTypography
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun CreateMatchScreen(
    state: CreateMatchUiState,
    onBackClick: () -> Unit = {},
    onCreateMatchClick: (
        week: String,
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        minute: Int,
        teamAName: String,
        teamBName: String,
        location: String,
        notes: String
    ) -> Unit = { _, _, _, _, _, _, _, _, _, _ -> }
) {
    val calendar = remember { Calendar.getInstance() }

    var selectedDateMillis by remember { mutableLongStateOf(calendar.timeInMillis) }
    var selectedHour by remember { mutableIntStateOf(20) }
    var selectedMinute by remember { mutableIntStateOf(0) }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    var teamAName by remember { mutableStateOf("") }
    var teamBName by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    val selectedCalendar = remember(selectedDateMillis, selectedHour, selectedMinute) {
        Calendar.getInstance().apply {
            timeInMillis = selectedDateMillis
            set(Calendar.HOUR_OF_DAY, selectedHour)
            set(Calendar.MINUTE, selectedMinute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
    }

    if (showDatePicker) {
        MatchDatePickerDialog(
            selectedDateMillis = selectedDateMillis,
            onDismiss = { showDatePicker = false },
            onDateSelected = {
                selectedDateMillis = it
                showDatePicker = false
            }
        )
    }

    if (showTimePicker) {
        MatchTimePickerDialog(
            initialHour = selectedHour,
            initialMinute = selectedMinute,
            onDismiss = { showTimePicker = false },
            onTimeSelected = { hour, minute ->
                selectedHour = hour
                selectedMinute = minute
                showTimePicker = false
            }
        )
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar(
                title = "Create Match",
                style = TopBarStyle.Child,
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    onCreateMatchClick(
                        state.currentWeek.toString(),
                        selectedCalendar.get(Calendar.YEAR),
                        selectedCalendar.get(Calendar.MONTH),
                        selectedCalendar.get(Calendar.DAY_OF_MONTH),
                        selectedCalendar.get(Calendar.HOUR_OF_DAY),
                        selectedCalendar.get(Calendar.MINUTE),
                        teamAName,
                        teamBName,
                        location,
                        notes
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp, vertical = 16.dp)
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
                    Icon(Icons.Default.CalendarMonth, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Create Match",
                        fontWeight = KickOffTypography.ExtraBold
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp)
                .padding(bottom = 90.dp)
        ) {
            InputCard("MATCH DETAILS") {
                ReadOnlyInfoField(
                    label = "WEEK",
                    value = "Week ${state.currentWeek}"
                )

                Spacer(modifier = Modifier.height(12.dp))

                SelectorField(
                    label = "DATE",
                    value = formatDate(selectedCalendar),
                    icon = Icons.Default.CalendarMonth,
                    onClick = { showDatePicker = true }
                )

                Spacer(modifier = Modifier.height(12.dp))

                SelectorField(
                    label = "TIME",
                    value = formatTime(selectedCalendar),
                    icon = Icons.Default.Schedule,
                    onClick = { showTimePicker = true }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            InputCard("TEAMS") {
                MatchTextField(
                    label = "TEAM A",
                    value = teamAName,
                    onValueChange = { teamAName = it },
                    placeholder = "e.g. Claros"
                )

                Spacer(modifier = Modifier.height(12.dp))

                MatchTextField(
                    label = "TEAM B",
                    value = teamBName,
                    onValueChange = { teamBName = it },
                    placeholder = "e.g. Escuros"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            InputCard("EXTRA INFO") {
                MatchTextField(
                    label = "LOCATION",
                    value = location,
                    onValueChange = { location = it },
                    placeholder = "e.g. Campo do Montijo"
                )

                Spacer(modifier = Modifier.height(12.dp))

                MatchTextField(
                    label = "NOTES",
                    value = notes,
                    onValueChange = { notes = it },
                    placeholder = "Optional notes"
                )
            }

            state.errorMessage?.let {
                Spacer(modifier = Modifier.height(14.dp))
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

@Composable
private fun InputCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
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
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = KickOffTypography.CardTitleSize,
            fontWeight = KickOffTypography.ExtraBold
        )

        Spacer(modifier = Modifier.height(14.dp))

        content()
    }
}

@Composable
private fun ReadOnlyInfoField(
    label: String,
    value: String
) {
    Column {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.primary,
            fontSize = KickOffTypography.LabelSize,
            fontWeight = KickOffTypography.ExtraBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .background(
                    color = KickOffTheme.colors.input,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(horizontal = 16.dp),
            contentAlignment = androidx.compose.ui.Alignment.CenterStart
        ) {
            Text(
                text = value,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = KickOffTypography.BodySize,
                fontWeight = KickOffTypography.Bold
            )
        }
    }
}

@Composable
private fun SelectorField(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Column {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.primary,
            fontSize = KickOffTypography.LabelSize,
            fontWeight = KickOffTypography.ExtraBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedButton(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = KickOffTheme.colors.input,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = value,
                fontSize = KickOffTypography.BodySize,
                fontWeight = KickOffTypography.Bold
            )
        }
    }
}

@Composable
private fun MatchTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = ""
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.primary,
            fontSize = KickOffTypography.LabelSize,
            fontWeight = KickOffTypography.ExtraBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            placeholder = {
                Text(
                    text = placeholder,
                    color = KickOffTheme.colors.muted,
                    fontSize = KickOffTypography.SmallSize,
                    fontWeight = KickOffTypography.Bold
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(10.dp),
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
}

@Composable
private fun MatchDatePickerDialog(
    selectedDateMillis: Long,
    onDismiss: () -> Unit,
    onDateSelected: (Long) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDateMillis
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let {
                        onDateSelected(it)
                    }
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                selectedDayContainerColor = MaterialTheme.colorScheme.primary,
                todayDateBorderColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Composable
private fun MatchTimePickerDialog(
    initialHour: Int,
    initialMinute: Int,
    onDismiss: () -> Unit,
    onTimeSelected: (Int, Int) -> Unit
) {
    val timePickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = true
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = KickOffTheme.colors.card,
        confirmButton = {
            TextButton(
                onClick = {
                    onTimeSelected(
                        timePickerState.hour,
                        timePickerState.minute
                    )
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = {
            Text(
                text = "Select time",
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = KickOffTypography.ExtraBold
            )
        },
        text = {
            TimePicker(
                state = timePickerState,
                colors = TimePickerDefaults.colors(
                    selectorColor = MaterialTheme.colorScheme.primary,
                    clockDialColor = KickOffTheme.colors.input
                )
            )
        }
    )
}

private fun formatDate(calendar: Calendar): String {
    val formatter = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.ENGLISH)
    return formatter.format(calendar.time)
}

private fun formatTime(calendar: Calendar): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.ENGLISH)
    return formatter.format(calendar.time)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateMatchScreenPreview() {
    KickOffTheme {
        CreateMatchScreen(
            state = CreateMatchUiState(currentWeek = 4)
        )
    }
}