package com.example.kickoff.ui.screens

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Leagues : Screen("leagues")
    object Profile : Screen("profile")
    object CreateLeague : Screen("create_league")
    object LeagueDashboard : Screen("league_dashboard/{leagueId}") {
        fun createRoute(leagueId: String): String {
            return "league_dashboard/$leagueId"
        }
    }
    object LeagueAdmin : Screen("league_admin/{leagueId}") {
        fun createRoute(leagueId: String): String {
            return "league_admin/$leagueId"
        }
    }
    object FantasyTeam : Screen("fantasy_team/{leagueId}") {
        fun createRoute(leagueId: String): String {
            return "fantasy_team/$leagueId"
        }
    }
    object Matches : Screen("matches/{leagueId}") {
        fun createRoute(leagueId: String): String {
            return "matches/$leagueId"
        }
    }
    object CreateMatch : Screen("create_match/{leagueId}") {
        fun createRoute(leagueId: String): String {
            return "create_match/$leagueId"
        }
    }
    object MatchReport : Screen("match_report/{leagueId}/{matchId}") {
        fun createRoute(
            leagueId: String,
            matchId: String
        ): String {
            return "match_report/$leagueId/$matchId"
        }
    }
    object MatchLineup : Screen("match_lineup/{leagueId}/{matchId}") {
        fun createRoute(
            leagueId: String,
            matchId: String
        ): String {
            return "match_lineup/$leagueId/$matchId"
        }
    }
    object Ranking : Screen("ranking/{leagueId}") {
        fun createRoute(leagueId: String): String {
            return "ranking/$leagueId"
        }
    }
    object JoinLeague : Screen("join_league")
    object Players : Screen("players/{leagueId}") {
        fun createRoute(leagueId: String) = "players/$leagueId"
    }
    object Rules : Screen("rules/{leagueId}") {
        fun createRoute(leagueId: String) = "rules/$leagueId"
    }
}