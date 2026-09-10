package com.example.kickoff.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = KickOffDarkColors.Primary,
    background = KickOffDarkColors.Background,
    surface = KickOffDarkColors.Card,
    onPrimary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    error = KickOffDarkColors.Red,
    tertiary = KickOffDarkColors.CardGradient
)

private val LightColorScheme = lightColorScheme(
    primary = KickOffLightColors.Primary,
    background = KickOffLightColors.Background,
    surface = KickOffLightColors.Card,
    onPrimary = Color.White,
    onBackground = Color(0xFF111411),
    onSurface = Color(0xFF111411),
    error = KickOffLightColors.Red,
    tertiary = KickOffLightColors.CardGradient
)

data class KickOffExtraColors(
    val card: Color,
    val input: Color,
    val muted: Color,
    val yellow: Color
)

private val LocalKickOffColors =
    staticCompositionLocalOf<KickOffExtraColors> {
        error("No KickOff colors provided")
    }

object KickOffTheme {

    val colors: KickOffExtraColors
        @Composable
        get() = LocalKickOffColors.current
}

@Composable
fun KickOffTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme =
        if (darkTheme) DarkColorScheme
        else LightColorScheme

    val extraColors =
        if (darkTheme) {
            KickOffExtraColors(
                card = KickOffDarkColors.Card,
                input = KickOffDarkColors.Input,
                muted = KickOffDarkColors.Muted,
                yellow = KickOffDarkColors.Yellow
            )
        } else {
            KickOffExtraColors(
                card = KickOffLightColors.Card,
                input = KickOffLightColors.Input,
                muted = KickOffLightColors.Muted,
                yellow = KickOffLightColors.Yellow
            )
        }

    CompositionLocalProvider(
        LocalKickOffColors provides extraColors
    ) {

        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )

    }
}