package com.example.kickoff

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.kickoff.ui.navigation.AppNavGraph
import com.example.kickoff.ui.theme.KickOffTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KickOffTheme {
                AppNavGraph()
            }
        }
    }
}

