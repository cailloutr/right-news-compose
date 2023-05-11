package com.cailloutr.rightnewscompose.navigation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.cailloutr.rightnewscompose.constants.Constants
import com.cailloutr.rightnewscompose.model.SectionWrapper
import com.cailloutr.rightnewscompose.navigation.Args.SECTION_ID
import com.cailloutr.rightnewscompose.navigation.Args.SECTION_TITLE
import com.cailloutr.rightnewscompose.other.Status.*
import com.cailloutr.rightnewscompose.other.getNetworkMessage
import com.cailloutr.rightnewscompose.ui.screens.latestnewsscreen.LatestNewsScreen
import com.cailloutr.rightnewscompose.ui.viewmodel.LatestNewsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.latestNewsNavGraph(
    navigationActions: RightNewsNavigationActions,
    snackbarHostState: SnackbarHostState,
    context: Context,
) {
    navigation(
        route = Graph.LATEST_NEWS,
        startDestination = LatestNewsScreen.LatestNews.route + "/{$SECTION_ID}" + "/{$SECTION_TITLE}"
    ) {
        composable(
            route = LatestNewsScreen.LatestNews.route + "/{$SECTION_ID}" + "/{$SECTION_TITLE}",
            arguments = listOf(
                navArgument(SECTION_ID) {
                    type = NavType.StringType
                },
                navArgument(SECTION_TITLE) {
                    type = NavType.StringType
                }
            )
        ) { entry ->

            val sectionId by remember {
                mutableStateOf(entry.arguments?.getString(SECTION_ID))
            }
            val sectionTitle by remember {
                mutableStateOf(entry.arguments?.getString(SECTION_TITLE))
            }

            val viewModel = hiltViewModel<LatestNewsViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            val scope = rememberCoroutineScope()

            val pullRefresh: (CoroutineScope) -> Unit = {
                viewModel.getLatestNewsBySection(
                    if (sectionId == Constants.LATEST_NEWS) {
                        SectionWrapper(sectionId!!, "")
                    } else {
                        SectionWrapper(sectionId!!, sectionId!!)
                    }
                ) { response ->
                    val message: Int? = response.status.getNetworkMessage()

                    scope.launch {
                        message?.let {
                            snackbarHostState.showSnackbar(context.getString(it))
                        }
                    }
                }
            }

            LaunchedEffect(Unit) {
                scope.launch {
                    sectionTitle?.let {
                        viewModel.setUiStateTitle(sectionTitle!!)
                    }

                    sectionId?.let {
                        viewModel.getLatestNewsBySection(
                            if (sectionId == Constants.LATEST_NEWS) {
                                SectionWrapper(sectionId!!, "")
                            } else {
                                SectionWrapper(sectionId!!, sectionId!!)
                            }
                        ) {}
                    }
                }
            }

            LatestNewsScreen(
                uiState = uiState,
                navigateUp = {
                    navigationActions.navigateUp()
                },
                navigateToDetails = { id ->
                    navigationActions.navigateToDetails(id)
                },
                pullRefresh = {
                    pullRefresh(scope)
                },
            )
        }
    }
}