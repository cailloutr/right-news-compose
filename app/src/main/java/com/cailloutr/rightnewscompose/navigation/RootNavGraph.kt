package com.cailloutr.rightnewscompose.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cailloutr.rightnewscompose.ui.screens.HomeScreen

//TODO: Test navigation
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.HOME
    ) {
        composable(route = Graph.HOME) {
            HomeScreen()
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val HOME = "home_graph"
    const val DETAILS = "details_graph"
    const val ALL_SECTIONS = "all_sections_graph"
    const val LATEST_NEWS = "latest_news_graph"
    const val SEARCH_NEWS = "search_news_graph"
}