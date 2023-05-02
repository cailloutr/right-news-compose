package com.cailloutr.rightnewscompose.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.cailloutr.rightnewscompose.extensions.fromRouteId
import com.cailloutr.rightnewscompose.ui.screens.detailscreen.DetailsScreen
import com.cailloutr.rightnewscompose.ui.screens.mainscreen.MainScreen
import com.cailloutr.rightnewscompose.ui.viewmodel.DetailsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeNavGraph(navController: NavHostController) {
    val viewModel = hiltViewModel<DetailsViewModel>()
    val navigationActions = RightNewsNavigationActions(navController)

    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreens.Main.route
    ) {
        composable(route = BottomBarScreens.Main.route) {
            MainScreen(
                navigateToDetails = { id ->
                    navigationActions.navigateToDetails(id)
                }
            )
        }

        composable(route = BottomBarScreens.Favorite.route) {}
        composable(route = BottomBarScreens.Profile.route) {}
        detailsNavGraph(
            viewModel = viewModel,
            navigationActions = navigationActions
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.detailsNavGraph(
    navigationActions: RightNewsNavigationActions,
    viewModel: DetailsViewModel,
) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.Details.route + "/{${Args.ARTICLE_ID}}"
    ) {
        composable(
            route = DetailsScreen.Details.route + "/{${Args.ARTICLE_ID}}",
            arguments = listOf(
                navArgument(Args.ARTICLE_ID) {
                    type = NavType.StringType
                }
            )
        ) {
            viewModel.getArticleById(
                it.arguments?.getString(Args.ARTICLE_ID, "")?.fromRouteId() ?: ""
            )
            val uiState = viewModel.uiState.collectAsState()
            DetailsScreen(
                uiState = uiState.value,
                navigateUp = { navigationActions.navigateUp() },
            )
        }
    }
}
