package com.cailloutr.rightnewscompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cailloutr.rightnewscompose.HomeScreens
import com.cailloutr.rightnewscompose.ui.screens.MainScreen

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = HomeScreens.MainScreen.route
    ) {
        composable(route = HomeScreens.MainScreen.route) {
            MainScreen()
        }

        composable(route = HomeScreens.FavoriteScreen.route) {}
        composable(route = HomeScreens.ProfileScreen.route) {}
    }
}