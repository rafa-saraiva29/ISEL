package com.example.kickoff.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kickoff.ui.states.AuthUiState
import com.example.kickoff.ui.theme.KickOffTheme
import com.example.kickoff.ui.theme.KickOffTypography

@Composable
fun RegisterScreen(
    state: AuthUiState,
    onRegisterClick: (String, String, String, String) -> Unit,
    onLoginClick: () -> Unit,
    onGoogleClick: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(62.dp))

            Text(
                text = "Create Account",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = KickOffTypography.CardTitleSize,
                fontWeight = KickOffTypography.Bold
            )

            Spacer(modifier = Modifier.height(22.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(KickOffTheme.colors.card)
                    .padding(22.dp)
            ) {
                FormField(
                    label = "Full Name",
                    value = fullName,
                    onValueChange = { fullName = it },
                    placeholder = "Enter your name",
                    leadingIcon = {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            tint = KickOffTheme.colors.muted
                        )
                    }
                )

                Spacer(modifier = Modifier.height(14.dp))

                FormField(
                    label = "Email Address",
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "manager@kickoff.com",
                    leadingIcon = {
                        Icon(
                            Icons.Default.Email,
                            contentDescription = null,
                            tint = KickOffTheme.colors.muted
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    )
                )

                Spacer(modifier = Modifier.height(14.dp))

                FormField(
                    label = "Password",
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "••••••••",
                    leadingIcon = {
                        Icon(
                            Icons.Default.Lock,
                            contentDescription = null,
                            tint = KickOffTheme.colors.muted
                        )
                    },
                    visualTransformation =
                        if (passwordVisible) VisualTransformation.None
                        else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            onClick = { passwordVisible = !passwordVisible }
                        ) {
                            Icon(
                                imageVector =
                                    if (passwordVisible) Icons.Default.VisibilityOff
                                    else Icons.Default.Visibility,
                                contentDescription = null,
                                tint = KickOffTheme.colors.muted
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(14.dp))

                FormField(
                    label = "Confirm Password",
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholder = "••••••••",
                    leadingIcon = {
                        Icon(
                            Icons.Default.Shield,
                            contentDescription = null,
                            tint = KickOffTheme.colors.muted
                        )
                    },
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        onRegisterClick(
                            fullName,
                            email,
                            password,
                            confirmPassword
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
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
                        Text(
                            text = "Create Account",
                            fontSize = KickOffTypography.BodySize,
                            fontWeight = KickOffTypography.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = KickOffTheme.colors.input
                    )

                    Text(
                        text = "OR CONTINUE WITH",
                        color = KickOffTheme.colors.muted,
                        fontSize = KickOffTypography.SmallSize,
                        fontWeight = KickOffTypography.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )

                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = KickOffTheme.colors.input
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onGoogleClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = KickOffTheme.colors.input,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Text(
                        text = "Google",
                        fontSize = KickOffTypography.BodySize,
                        fontWeight = KickOffTypography.Bold
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Already have an account? ",
                        color = KickOffTheme.colors.muted,
                        fontSize = KickOffTypography.SmallSize,
                        fontWeight = KickOffTypography.Bold
                    )

                    TextButton(
                        onClick = onLoginClick,
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.height(18.dp)
                    ) {
                        Text(
                            text = "Login",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = KickOffTypography.SmallSize,
                            fontWeight = KickOffTypography.Bold
                        )
                    }
                }
            }

            state.errorMessage?.let { message ->
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = KickOffTypography.SmallSize,
                    fontWeight = KickOffTypography.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun FormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: @Composable () -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Column {
        Text(
            text = label,
            color = KickOffTheme.colors.muted,
            fontSize = KickOffTypography.SmallSize,
            fontWeight = KickOffTypography.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            singleLine = true,
            placeholder = {
                Text(
                    text = placeholder,
                    color = KickOffTheme.colors.muted,
                    fontSize = KickOffTypography.BodySize,
                    fontWeight = KickOffTypography.Bold
                )
            },
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            shape = RoundedCornerShape(8.dp),
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
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    KickOffTheme {
        RegisterScreen(
            state = AuthUiState(),
            onRegisterClick = { _, _, _, _ -> },
            onLoginClick = {},
            onGoogleClick = {}
        )
    }
}