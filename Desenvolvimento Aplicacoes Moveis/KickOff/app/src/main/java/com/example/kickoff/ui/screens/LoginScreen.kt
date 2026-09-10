package com.example.kickoff.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kickoff.ui.states.AuthUiState
import com.example.kickoff.ui.theme.KickOffTheme
import com.example.kickoff.ui.theme.KickOffTypography

@Composable
fun LoginScreen(
    state: AuthUiState,
    onLoginClick: (String, String) -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit = {},
    onGoogleClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "Welcome to KickOff Fantasy!",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = KickOffTypography.SectionTitleSize,
            fontWeight = KickOffTypography.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Sign in and start managing your fantasy teams.",
            color = MaterialTheme.colorScheme.primary,
            fontSize = KickOffTypography.LabelSize
        )

        Spacer(modifier = Modifier.height(34.dp))

        LoginLabel("Email")

        LoginTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = "your@email.com",
            leadingIcon = Icons.Default.Email
        )

        Spacer(modifier = Modifier.height(22.dp))

        LoginLabel("Password")

        LoginTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = "••••••••",
            leadingIcon = Icons.Default.Lock,
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = null,
                        tint = KickOffTheme.colors.muted
                    )
                }
            },
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))

            TextButton(onClick = onForgotPasswordClick) {
                Text(
                    text = "Forgot password?",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = KickOffTypography.SmallSize,
                    fontWeight = KickOffTypography.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                onLoginClick(email, password)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
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
                    text = "Login",
                    fontSize = KickOffTypography.CardTitleSize,
                    fontWeight = KickOffTypography.Bold
                )

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(34.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = KickOffTheme.colors.input
            )

            Text(
                text = "OR",
                color = KickOffTheme.colors.muted,
                fontSize = KickOffTypography.LabelSize,
                modifier = Modifier.padding(horizontal = 14.dp)
            )

            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = KickOffTheme.colors.input
            )
        }

        Spacer(modifier = Modifier.height(26.dp))

        Button(
            onClick = onGoogleClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = KickOffTheme.colors.input,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Text(
                text = "Sign in with Google",
                fontSize = KickOffTypography.BodySize,
                fontWeight = KickOffTypography.Bold
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row {
            Text(
                text = "Don't have an account? ",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = KickOffTypography.SmallSize
            )

            Text(
                text = "Create account",
                color = MaterialTheme.colorScheme.primary,
                fontSize = KickOffTypography.SmallSize,
                fontWeight = KickOffTypography.Bold,
                modifier = Modifier.clickable {
                    onRegisterClick()
                }
            )
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

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
private fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: androidx.compose.ui.graphics.vector.ImageVector,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = KickOffTheme.colors.muted
            )
        },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = KickOffTheme.colors.muted
            )
        },
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = KickOffTheme.colors.card,
            unfocusedContainerColor = KickOffTheme.colors.card,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = KickOffTheme.colors.input,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
private fun LoginLabel(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onSurface,
        fontSize = KickOffTypography.SmallSize,
        fontWeight = KickOffTypography.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    KickOffTheme {
        LoginScreen(
            state = AuthUiState(),
            onLoginClick = { _, _ -> },
            onRegisterClick = {}
        )
    }
}