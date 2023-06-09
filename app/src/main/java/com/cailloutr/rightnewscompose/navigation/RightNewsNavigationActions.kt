package com.cailloutr.rightnewscompose.navigation

import androidx.navigation.NavController

class RightNewsNavigationActions(navController: NavController) {
    val navigateToSearchNewsScreen: () -> Unit = {
        val route = SearchNewsScreen.Search.route
        navController.navigate(route)
    }

    val navigateToLatestNews: (id: String, title: String) -> Unit = { id, title ->
        val route = LatestNewsScreen.LatestNews.withArgs(id, title)
        navController.navigate(route)
    }

    val navigateToDetails: (id: String) -> Unit = { id ->
        val route = DetailsScreen.Details.withArgs(id)
        navController.navigate(route)
    }
    val navigateToAllSections: () -> Unit = {
        val route = AllSectionsScreen.AllSections.route
        navController.navigate(route)
    }
    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }
}