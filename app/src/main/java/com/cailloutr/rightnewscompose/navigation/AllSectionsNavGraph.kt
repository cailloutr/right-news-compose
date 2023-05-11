package com.cailloutr.rightnewscompose.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.cailloutr.rightnewscompose.ui.screens.allsectionsscreen.AllSectionsScreen
import com.cailloutr.rightnewscompose.ui.viewmodel.AllSectionsViewModel

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.allSectionsNavGraph(
    navigationActions: RightNewsNavigationActions,
    viewModel: AllSectionsViewModel,
) {
    navigation(
        route = Graph.ALL_SECTIONS,
        startDestination = AllSectionsScreen.AllSections.route
    ) {
        composable(route = AllSectionsScreen.AllSections.route) {
            val uiState = viewModel.uiState.collectAsState()
            AllSectionsScreen(
                uiState = uiState.value,
                navigateToLatestNewsScreen = { id, title ->
                    navigationActions.navigateToLatestNews(id, title)
                }
            )
        }
    }
}