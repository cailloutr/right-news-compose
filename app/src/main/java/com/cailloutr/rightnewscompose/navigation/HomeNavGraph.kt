package com.cailloutr.rightnewscompose.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cailloutr.rightnewscompose.ui.screens.mainscreen.MainScreen
import com.cailloutr.rightnewscompose.ui.viewmodel.AllSectionsViewModel
import com.cailloutr.rightnewscompose.ui.viewmodel.DetailsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeNavGraph(navController: NavHostController, snackbarHostState: SnackbarHostState) {

    val viewModel = hiltViewModel<DetailsViewModel>()
    val allSectionsViewModel = hiltViewModel<AllSectionsViewModel>()
//    val latestNewsViewModel = hiltViewModel<LatestNewsViewModel>()

    val navigationActions = RightNewsNavigationActions(navController)
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreens.Main.route
    ) {
        composable(route = BottomBarScreens.Main.route) {
            MainScreen(
                navigateToDetails = { id ->
                    navigationActions.navigateToDetails(id)
                },
                navigateToAllSections = {
                    navigationActions.navigateToAllSections()
                },
                navigateToLatestNews = { id, title ->
                    navigationActions.navigateToLatestNews(id, title)
                },
                snackbarHostState = snackbarHostState,
                context = context
            )
        }
        composable(route = BottomBarScreens.Favorite.route) {}
        composable(route = BottomBarScreens.Profile.route) {}
        detailsNavGraph(
            viewModel = viewModel,
            navigationActions = navigationActions,
            context = context,
            uriHandler = uriHandler
        )
        allSectionsNavGraph(
            navigationActions = navigationActions,
            viewModel = allSectionsViewModel
        )
        latestNewsNavGraph(
            navigationActions = navigationActions,
            snackbarHostState = snackbarHostState,
            context = context
        )
    }
}
