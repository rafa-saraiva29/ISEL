package com.example.kickoff.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.kickoff.ui.screens.Screen
import com.example.kickoff.ui.theme.KickOffTheme
import com.example.kickoff.ui.theme.KickOffTypography

@Composable
fun BottomNavBar(
    navController: NavController
) {
    val currentRoute =
        navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {

        bottomNavItems.forEach { item ->

            NavigationBarItem(
                selected = currentRoute == item.route,

                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(Screen.Home.route) {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    }
                },

                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },

                label = {
                    Text(
                        text = item.label,
                        fontSize = KickOffTypography.SmallSize,
                        fontWeight = KickOffTypography.Bold
                    )
                },

                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,

                    unselectedIconColor = KickOffTheme.colors.muted,
                    unselectedTextColor = KickOffTheme.colors.muted,

                    indicatorColor = androidx.compose.ui.graphics.Color.Transparent
                )
            )

        }

    }
}