package com.cailloutr.rightnewscompose.navigation

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.UriHandler
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.cailloutr.rightnewscompose.R
import com.cailloutr.rightnewscompose.extensions.fromRouteId
import com.cailloutr.rightnewscompose.extensions.shareLinkIntent
import com.cailloutr.rightnewscompose.ui.screens.detailscreen.DetailsScreen
import com.cailloutr.rightnewscompose.ui.viewmodel.DetailsViewModel

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.detailsNavGraph(
    navigationActions: RightNewsNavigationActions,
    context: Context,
    uriHandler: UriHandler,
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
            val viewModel = hiltViewModel<DetailsViewModel>()
            viewModel.getArticleById(
                it.arguments?.getString(Args.ARTICLE_ID, "")?.fromRouteId() ?: ""
            )

            Log.i("DetailsNavGraph", "detailsNavGraph: ${it.arguments?.getString(Args.ARTICLE_ID, "")?.fromRouteId() ?: ""}")

            val uiState = viewModel.uiState.collectAsState()
            DetailsScreen(
                uiState = uiState.value,
                navigateUp = { navigationActions.navigateUp() },
                share = { url ->
                    context.shareLinkIntent(
                        title = R.string.share_link,
                        value = url
                    )
                },
                openLink = { url ->
                    uriHandler.openUri(url)
                },
            )
        }
    }
}
