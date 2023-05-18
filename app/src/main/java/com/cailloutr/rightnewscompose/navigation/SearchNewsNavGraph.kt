package com.cailloutr.rightnewscompose.navigation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.cailloutr.rightnewscompose.R
import com.cailloutr.rightnewscompose.other.Status
import com.cailloutr.rightnewscompose.ui.screens.searchscreen.SearchScreen
import com.cailloutr.rightnewscompose.ui.viewmodel.SearchViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.searchNewsNavGraph(
    navigationActions: RightNewsNavigationActions,
    snackbarHostState: SnackbarHostState,
    context: Context,
) {
    navigation(
        route = Graph.SEARCH_NEWS,
        startDestination = SearchNewsScreen.Search.route,
    ) {
        composable(route = SearchNewsScreen.Search.route) {
            val viewModel = hiltViewModel<SearchViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            val lifeCycle = LocalLifecycleOwner.current.lifecycle

            val snackbarState by viewModel.snackbarState.collectAsStateWithLifecycle(
                initialValue = null,
                lifecycle = lifeCycle
            )

            val scope = rememberCoroutineScope()

            val searchArticle = { query: String ->
                scope.launch {
                    viewModel.getSearchResult(query) { response ->
                        viewModel.setUiStateStatus(response.status)
                        viewModel.setSnackbarState(response.status)
                    }
                }
            }

            LaunchedEffect(key1 = snackbarState) {
                if (snackbarState == Status.ERROR) {
                    scope.launch {
                        snackbarHostState.showSnackbar(message = context.getString(R.string.network_connection_error))
                    }
                }
            }

            SearchScreen(
                uiState = uiState,
                onSearch = { query ->
                    searchArticle(query)
                },
                navigateToDetails = { id ->
                    navigationActions.navigateToDetails(id)
                },
                navigateUp = {
                    navigationActions.navigateUp()
                },
            )
        }
    }
}