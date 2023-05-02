package com.cailloutr.rightnewscompose.navigation

import androidx.navigation.NavController

class RightNewsNavigationActions(navController: NavController) {
    val navigateToDetails: (id: String) -> Unit = { id ->
        val route = DetailsScreen.Details.withArgs(id)
        navController.navigate(route)
    }
    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }
}