package com.prcom.practicum.playlistproj.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prcom.practicum.playlistproj.screens.MenuScreen
import com.prcom.practicum.playlistproj.screens.SearchScreen
import com.prcom.practicum.playlistproj.screens.SettingsScreen

enum class Screen(val route: String) {
    MAIN_MENU("main_menu"),
    SEARCH("search"),
    SETTINGS("settings")
}

@Composable
fun PlaylistHost(
    startDestination: String = "main_menu",
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController, startDestination) {
        composable(route = Screen.MAIN_MENU.route) {
            MenuScreen(
                onSearchClick = { navController.navigate(Screen.SEARCH.route) },
                onSettingsClick = { navController.navigate(Screen.SETTINGS.route) }
            )
        }
        composable(route = Screen.SEARCH.route) {
            SearchScreen(onBackClick = { navController.navigate(Screen.MAIN_MENU.route) })
        }
        composable(route = Screen.SETTINGS.route) {
            SettingsScreen(onBackClick = { navController.navigate(Screen.MAIN_MENU.route) })
        }
    }
}
