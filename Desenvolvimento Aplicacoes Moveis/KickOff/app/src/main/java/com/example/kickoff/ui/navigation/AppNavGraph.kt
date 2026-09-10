package com.example.kickoff.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kickoff.data.GoogleSignInHelper
import com.example.kickoff.viewmodel.LeagueAdminViewModel
import com.example.kickoff.ui.components.BottomNavBar
import com.example.kickoff.viewmodel.FantasyTeamViewModel
import com.example.kickoff.ui.screens.CreateLeagueScreen
import com.example.kickoff.ui.screens.CreateMatchScreen
import com.example.kickoff.ui.screens.FantasyTeamScreen
import com.example.kickoff.viewmodel.LeaguesViewModel
import com.example.kickoff.ui.screens.HomeScreen
import com.example.kickoff.ui.screens.JoinLeagueScreen
import com.example.kickoff.ui.screens.LeagueAdminScreen
import com.example.kickoff.ui.screens.LeagueDashboardScreen
import com.example.kickoff.ui.screens.LeaguesScreen
import com.example.kickoff.ui.screens.LoginScreen
import com.example.kickoff.ui.screens.MatchLineupScreen
import com.example.kickoff.ui.screens.MatchReportScreen
import com.example.kickoff.ui.screens.MatchesScreen
import com.example.kickoff.ui.screens.PlayersScreen
import com.example.kickoff.ui.screens.ProfileScreen
import com.example.kickoff.ui.screens.RankingScreen
import com.example.kickoff.ui.screens.RegisterScreen
import com.example.kickoff.ui.screens.RulesScreen
import com.example.kickoff.ui.screens.Screen
import com.example.kickoff.viewmodel.AuthViewModel
import com.example.kickoff.viewmodel.HomeViewModel
import com.example.kickoff.viewmodel.LeagueDashboardViewModel
import com.example.kickoff.viewmodel.MatchLineupViewModel
import com.example.kickoff.viewmodel.MatchReportViewModel
import com.example.kickoff.viewmodel.MatchesViewModel
import com.example.kickoff.viewmodel.PlayersViewModel
import com.example.kickoff.viewmodel.ProfileViewModel
import com.example.kickoff.viewmodel.RankingViewModel
import kotlinx.coroutines.launch

@Composable
fun AppNavGraph(
    authViewModel: AuthViewModel = viewModel(),
    leaguesViewModel: LeaguesViewModel = viewModel(),
    leagueDashboardViewModel: LeagueDashboardViewModel = viewModel(),
    leagueAdminViewModel: LeagueAdminViewModel = viewModel(),
    fantasyTeamViewModel: FantasyTeamViewModel = viewModel(),
    matchesViewModel: MatchesViewModel = viewModel(),
    matchReportViewModel: MatchReportViewModel = viewModel(),
    matchLineupViewModel: MatchLineupViewModel = viewModel(),
    rankingViewModel: RankingViewModel = viewModel(),
    profileViewModel: ProfileViewModel = viewModel(),
    homeViewModel: HomeViewModel = viewModel(),
    playersViewModel: PlayersViewModel = viewModel()
) {
    val navController = rememberNavController()

    val authState = authViewModel.uiState
    val leaguesState = leaguesViewModel.uiState
    val leagueDashboardState = leagueDashboardViewModel.uiState
    val leagueAdminState = leagueAdminViewModel.uiState
    val fantasyTeamState = fantasyTeamViewModel.uiState
    val matchesState = matchesViewModel.uiState
    val matchReportState = matchReportViewModel.uiState
    val matchLineupState = matchLineupViewModel.uiState
    val rankingState = rankingViewModel.uiState
    val profileState = profileViewModel.uiState
    val homeState = homeViewModel.uiState
    val playersState = playersViewModel.uiState

    val context = LocalContext.current
    val googleSignInHelper = remember {
        GoogleSignInHelper(context)
    }

    val startDestination = remember {
        if (authViewModel.isUserLoggedIn()) {
            Screen.Home.route
        } else {
            Screen.Login.route
        }
    }

    val currentRoute =
        navController.currentBackStackEntryAsState().value?.destination?.route

    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.Leagues.route,
        Screen.Profile.route,
        Screen.LeagueDashboard.route
    )

    LaunchedEffect(authState.loginSuccess) {
        if (authState.loginSuccess) {
            navController.navigate("home") {
                popUpTo("login") {
                    inclusive = true
                }
            }
        }
    }

    LaunchedEffect(authState.registerSuccess) {
        if (authState.registerSuccess) {
            navController.navigate("login") {
                popUpTo("register") {
                    inclusive = true
                }
            }
        }
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navController)
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Login.route) {
                LoginScreen(
                    state = authState,
                    onLoginClick = { email, password ->
                        authViewModel.login(email, password)
                    },
                    onRegisterClick = {
                        navController.navigate("register")
                    },
                    onGoogleClick = {
                        authViewModel.viewModelScope.launch {
                            val idToken = googleSignInHelper.getGoogleIdToken()

                            if (idToken != null) {
                                authViewModel.loginWithGoogle(idToken)
                            } else {
                                Log.e("GoogleSignIn", "ID Token veio null")
                            }
                        }
                    }
                )
            }

            composable(Screen.Register.route) {
                RegisterScreen(
                    state = authState,
                    onRegisterClick = { fullName, email, password, confirmPassword ->
                        authViewModel.register(
                            fullName = fullName,
                            email = email,
                            password = password,
                            confirmPassword = confirmPassword
                        )
                    },
                    onLoginClick = {
                        navController.navigate("login")
                    },
                    onGoogleClick = {
                        authViewModel.viewModelScope.launch {
                            val idToken = googleSignInHelper.getGoogleIdToken()

                            if (idToken != null) {
                                authViewModel.loginWithGoogle(idToken)
                            } else {
                                Log.e("GoogleSignIn", "ID Token veio null")
                            }
                        }
                    }
                )
            }

            composable(Screen.Home.route) {
                LaunchedEffect(Unit) {
                    homeViewModel.loadNextMatch()
                    leaguesViewModel.loadUserLeagues()
                }

                HomeScreen(
                    homeState = homeState,
                    leaguesState = leaguesState,
                    onOpenNextMatchClick = { leagueId ->
                        navController.navigate(Screen.Matches.createRoute(leagueId))
                    },
                    onOpenLeagueClick = { league ->
                        navController.navigate(Screen.LeagueDashboard.createRoute(league.leagueId))
                    }
                )
            }

            composable(Screen.Leagues.route) {
                LaunchedEffect(Unit) {
                    leaguesViewModel.loadUserLeagues()
                }

                LeaguesScreen(
                    state = leaguesState,
                    onOpenLeagueClick = { league ->
                        navController.navigate(Screen.LeagueDashboard.createRoute(league.leagueId))
                    },
                    onCreateLeagueClick = {
                        navController.navigate(Screen.CreateLeague.route)
                    },
                    onJoinLeagueClick = {
                        navController.navigate(Screen.JoinLeague.route)
                    }
                )
            }

            composable(Screen.CreateLeague.route) {
                LaunchedEffect(leaguesState.createSuccess) {
                    if (leaguesState.createSuccess) {
                        leaguesViewModel.clearCreateSuccess()

                        navController.navigate(Screen.Leagues.route) {
                            popUpTo(Screen.CreateLeague.route) {
                                inclusive = true
                            }
                        }
                    }
                }

                CreateLeagueScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onCreateLeagueClick = { leagueName, maxMembers, inviteCode ->
                        leaguesViewModel.createLeague(
                            leagueName = leagueName,
                            maxMembers = maxMembers,
                            inviteCode = inviteCode
                        )
                    },

                )
            }

            composable(Screen.LeagueDashboard.route) { backStackEntry ->

                val leagueId = backStackEntry.arguments?.getString("leagueId") ?: ""

                LaunchedEffect(leagueId) {
                    leagueDashboardViewModel.loadLeagueDashboard(leagueId)
                }

                LeagueDashboardScreen(
                    state = leagueDashboardState,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onFantasyTeamClick = {
                        val leagueId = leagueDashboardState.league?.leagueId

                        if (leagueId != null) {
                            navController.navigate(
                                Screen.FantasyTeam.createRoute(leagueId)
                            )
                        }
                    },
                    onMatchesClick = {
                        val leagueId = leagueDashboardState.league?.leagueId
                        if (leagueId != null) {
                            navController.navigate(Screen.Matches.createRoute(leagueId))
                        }
                    },
                    onRankingClick = {
                        val leagueId = leagueDashboardState.league?.leagueId
                        if (leagueId != null) {
                            navController.navigate(Screen.Ranking.createRoute(leagueId))
                        }
                    },
                    onAdministrationClick = {
                        val leagueId = leagueDashboardState.league?.leagueId

                        if (leagueId != null) {
                            navController.navigate(
                                Screen.LeagueAdmin.createRoute(leagueId)
                            )
                        }
                    },
                    onPlayersClick = {
                        navController.navigate(Screen.Players.createRoute(leagueId))
                    },
                    onRulesClick = {
                        navController.navigate(Screen.Rules.createRoute(leagueId))
                    }
                )
            }

            composable(Screen.LeagueAdmin.route) { backStackEntry ->

                val leagueId = backStackEntry.arguments?.getString("leagueId") ?: ""

                LaunchedEffect(leagueId) {
                    leagueAdminViewModel.loadFantasyPlayers(leagueId)
                }

                LeagueAdminScreen(
                    state = leagueAdminState,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onAddPlayerClick = { name ->
                        leagueAdminViewModel.addFantasyPlayer(
                            leagueId = leagueId,
                            name = name
                        )
                    }
                )
            }

            composable(Screen.FantasyTeam.route) { backStackEntry ->

                val leagueId = backStackEntry.arguments?.getString("leagueId") ?: ""

                LaunchedEffect(leagueId) {
                    fantasyTeamViewModel.loadFantasyTeam(leagueId)
                }

                LaunchedEffect(fantasyTeamState.saveSuccess) {
                    if (fantasyTeamState.saveSuccess) {
                        fantasyTeamViewModel.clearSaveSuccess()
                        navController.popBackStack()
                    }
                }

                FantasyTeamScreen(
                    state = fantasyTeamState,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onSlotClick = { index ->
                        fantasyTeamViewModel.selectSlot(index)
                    },
                    onBenchPlayerClick = { player ->
                        fantasyTeamViewModel.selectBenchPlayer(player)
                    },
                    onSaveTeamClick = {
                        fantasyTeamViewModel.saveTeam(leagueId)
                    }
                )
            }

            composable(Screen.Matches.route) { backStackEntry ->

                val leagueId = backStackEntry.arguments?.getString("leagueId") ?: ""

                LaunchedEffect(leagueId) {
                    matchesViewModel.loadMatches(leagueId)
                }

                MatchesScreen(
                    state = matchesState,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onCreateMatchClick = {
                        navController.navigate(Screen.CreateMatch.createRoute(leagueId))
                    },
                    onMatchReportClick = { match ->
                        navController.navigate(
                            Screen.MatchReport.createRoute(
                                leagueId = leagueId,
                                matchId = match.matchId
                            )
                        )
                    },
                    onViewLineupClick = { match ->
                        navController.navigate(
                            Screen.MatchLineup.createRoute(
                                leagueId = leagueId,
                                matchId = match.matchId
                            )
                        )
                    }
                )
            }

            composable(Screen.CreateMatch.route) { backStackEntry ->

                val leagueId = backStackEntry.arguments?.getString("leagueId") ?: ""
                val createMatchState = matchesViewModel.createMatchState

                LaunchedEffect(createMatchState.createSuccess) {
                    if (createMatchState.createSuccess) {
                        matchesViewModel.clearCreateMatchSuccess()
                        matchesViewModel.loadMatches(leagueId)

                        navController.popBackStack()
                        matchesViewModel.loadMatches(leagueId)
                    }
                }

                LaunchedEffect(leagueId) {
                    matchesViewModel.loadCreateMatchData(leagueId)
                }

                CreateMatchScreen(
                    state = createMatchState,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onCreateMatchClick = { week, year, month, day, hour, minute, teamA, teamB, location, notes ->
                        matchesViewModel.createMatch(
                            leagueId = leagueId,
                            weekText = week,
                            year = year,
                            month = month,
                            day = day,
                            hour = hour,
                            minute = minute,
                            teamAName = teamA,
                            teamBName = teamB,
                            location = location,
                            notes = notes
                        )
                    }
                )
            }

            composable(Screen.MatchReport.route) { backStackEntry ->
                val leagueId = backStackEntry.arguments?.getString("leagueId") ?: ""
                val matchId = backStackEntry.arguments?.getString("matchId") ?: ""

                LaunchedEffect(leagueId, matchId) {
                    matchReportViewModel.loadMatchReport(
                        leagueId = leagueId,
                        matchId = matchId
                    )
                }

                LaunchedEffect(matchReportState.submitSuccess) {
                    if (matchReportState.submitSuccess) {
                        matchReportViewModel.clearSubmitSuccess()
                        matchesViewModel.loadMatches(leagueId)

                        navController.popBackStack()
                    }
                }

                MatchReportScreen(
                    state = matchReportState,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onTeamAScoreChange = { value ->
                        matchReportViewModel.updateTeamAScore(value)
                    },
                    onTeamBScoreChange = { value ->
                        matchReportViewModel.updateTeamBScore(value)
                    },
                    onPlayerTeamChange = { playerId, team ->
                        matchReportViewModel.setPlayerTeam(playerId, team)
                    },
                    onPlayerStatChange = { playerId, stat, delta ->
                        matchReportViewModel.updatePlayerStat(
                            playerId = playerId,
                            stat = stat,
                            delta = delta
                        )
                    },
                    onSubmitClick = {
                        matchReportViewModel.submitReport(
                            leagueId = leagueId,
                            matchId = matchId
                        )
                    }
                )
            }

            composable(Screen.MatchLineup.route) { backStackEntry ->
                val leagueId = backStackEntry.arguments?.getString("leagueId") ?: ""
                val matchId = backStackEntry.arguments?.getString("matchId") ?: ""

                LaunchedEffect(leagueId, matchId) {
                    matchLineupViewModel.loadLineup(
                        leagueId = leagueId,
                        matchId = matchId
                    )
                }

                MatchLineupScreen(
                    state = matchLineupState,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Screen.Ranking.route) { backStackEntry ->
                val leagueId = backStackEntry.arguments?.getString("leagueId") ?: ""

                LaunchedEffect(leagueId) {
                    rankingViewModel.loadRanking(leagueId)
                }

                RankingScreen(
                    state = rankingState,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Screen.Profile.route) {
                LaunchedEffect(Unit) {
                    profileViewModel.loadProfile()
                }

                LaunchedEffect(profileState.logoutSuccess) {
                    if (profileState.logoutSuccess) {
                        profileViewModel.clearLogoutSuccess()

                        navController.navigate(Screen.Login.route) {
                            popUpTo(0)
                        }
                    }
                }

                ProfileScreen(
                    state = profileState,
                    onLogoutClick = {
                        profileViewModel.logout()
                    }
                )
            }

            composable(Screen.JoinLeague.route) {
                LaunchedEffect(leaguesState.joinSuccess) {
                    if (leaguesState.joinSuccess) {
                        leaguesViewModel.clearJoinSuccess()
                        leaguesViewModel.loadUserLeagues()

                        navController.navigate(Screen.Leagues.route) {
                            popUpTo(Screen.JoinLeague.route) {
                                inclusive = true
                            }
                        }
                    }
                }

                JoinLeagueScreen(
                    state = leaguesState,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onJoinLeagueClick = { leagueName, inviteCode ->
                        leaguesViewModel.joinLeague(
                            leagueName = leagueName,
                            inviteCode = inviteCode
                        )
                    }
                )
            }

            composable(
                route = Screen.Players.route
            ) { backStackEntry ->

                val leagueId = backStackEntry.arguments?.getString("leagueId") ?: ""

                LaunchedEffect(leagueId) {
                    playersViewModel.loadPlayers(leagueId)
                }

                PlayersScreen(
                    state = playersState,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Screen.Rules.route) {
                RulesScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}