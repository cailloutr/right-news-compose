package com.cailloutr.rightnewscompose.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cailloutr.rightnewscompose.R
import com.cailloutr.rightnewscompose.other.Status
import com.cailloutr.rightnewscompose.other.getNetworkMessage
import com.cailloutr.rightnewscompose.ui.screens.mainscreen.MainScreen
import com.cailloutr.rightnewscompose.ui.viewmodel.AllSectionsViewModel
import com.cailloutr.rightnewscompose.ui.viewmodel.NewsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeNavGraph(navController: NavHostController, snackbarHostState: SnackbarHostState) {

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

            val viewModel = hiltViewModel<NewsViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val scope = rememberCoroutineScope()

            val pullRefreshState = rememberPullRefreshState(
                refreshing = uiState.isRefreshingAll,
                onRefresh = {
                    viewModel.refreshData() { response ->
                        val message: Int? = response.status.getNetworkMessage()

                        scope.launch {
                            message?.let {
                                snackbarHostState.showSnackbar(context.getString(it))
                            }
                        }
                    }
                }
            )

            LaunchedEffect(key1 = Unit) {
                scope.launch {
                    viewModel.refreshData()
                }
            }

            MainScreen(
                uiState = uiState,
                navigateToDetails = { id ->
                    navigationActions.navigateToDetails(id)
                },
                navigateToAllSections = {
                    navigationActions.navigateToAllSections()
                },
                navigateToLatestNews = { id, title ->
                    navigationActions.navigateToLatestNews(id, title)
                },
                pullRefreshState = pullRefreshState,
                onSectionSelectedListener = { id, _ ->
                    viewModel.setSelectedSection(id)
                    viewModel.getNewsBySection { response ->
                        when (response.status) {
                            Status.ERROR -> {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = context.getString(R.string.network_connection_error),
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }

                            else -> {}
                        }
                    }
                }
            )
        }
        composable(route = BottomBarScreens.Favorite.route) {}
        composable(route = BottomBarScreens.Profile.route) {}
        detailsNavGraph(
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
