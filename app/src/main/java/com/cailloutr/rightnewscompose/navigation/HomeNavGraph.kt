package com.cailloutr.rightnewscompose.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
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
import com.cailloutr.rightnewscompose.ui.screens.profilescreen.NotLoggedScreen
import com.cailloutr.rightnewscompose.ui.screens.profilescreen.ProfileScreen
import com.cailloutr.rightnewscompose.ui.viewmodel.AllSectionsViewModel
import com.cailloutr.rightnewscompose.ui.viewmodel.AuthViewModel
import com.cailloutr.rightnewscompose.ui.viewmodel.NewsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeNavGraph(navController: NavHostController, snackbarHostState: SnackbarHostState) {

    val allSectionsViewModel = hiltViewModel<AllSectionsViewModel>()
    val authViewModel = hiltViewModel<AuthViewModel>()

    val navigationActions = RightNewsNavigationActions(navController)
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreens.Main.route
    ) {
        composable(route = BottomBarScreens.Main.route) {

            val newsViewModel = hiltViewModel<NewsViewModel>()

            val uiState by newsViewModel.uiState.collectAsStateWithLifecycle()
            val scope = rememberCoroutineScope()

            val loginStatus by authViewModel.isLoggedIn.collectAsStateWithLifecycle(initialValue = false)

            val pullRefreshState = rememberPullRefreshState(
                refreshing = uiState.isRefreshingAll,
                onRefresh = {
                    newsViewModel.refreshData() { response ->
                        val message: Int? = response.status.getNetworkMessage()

                        scope.launch {
                            message?.let {
                                snackbarHostState.showSnackbar(context.getString(it))
                            }
                        }
                    }
                }
            )

            //TODO: fix bug showing snack-bar every time user go to the main screen after login
            LaunchedEffect(key1 = loginStatus) {
                authViewModel.loginStatus()
                if (loginStatus) {
                    scope.launch {
                        snackbarHostState.showSnackbar("Logged in Successfully")
                    }
                }
            }

            LaunchedEffect(key1 = Unit) {
                scope.launch {
                    newsViewModel.refreshData()
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
                navigateToSearchScreen = {
                    navigationActions.navigateToSearchNewsScreen()
                },
                onSectionSelectedListener = { id, _ ->
                    newsViewModel.setSelectedSection(id)
                    newsViewModel.getNewsBySection { response ->
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
        composable(route = BottomBarScreens.Profile.route) {
            val profileUiState by authViewModel.profileUiState.collectAsStateWithLifecycle()

            Crossfade(targetState = profileUiState.isLoggedIn, label = "") {
                when (it) {
                    true -> {
                        ProfileScreen(
                            uiState = profileUiState,
                            logout = {
                                authViewModel.logout()
                            }
                        )
                    }

                    false -> {
                        NotLoggedScreen(navigateToLogin = { navigationActions.navigateToLogin() })
                    }
                }
            }
        }
        authNavGraph(
            navigationActions = navigationActions,
            authViewModel = authViewModel,
        )
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
        searchNewsNavGraph(
            navigationActions = navigationActions,
            snackbarHostState = snackbarHostState,
            context = context
        )
    }
}
