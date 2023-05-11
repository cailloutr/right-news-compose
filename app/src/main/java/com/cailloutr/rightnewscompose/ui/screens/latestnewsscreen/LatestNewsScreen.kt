package com.cailloutr.rightnewscompose.ui.screens.latestnewsscreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cailloutr.rightnewscompose.R
import com.cailloutr.rightnewscompose.ui.components.SmallAppBar
import com.cailloutr.rightnewscompose.ui.screens.mainscreen.NewsSectionsCard
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme
import com.cailloutr.rightnewscompose.ui.uistate.LatestNewsUiState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LatestNewsScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    navigateToDetails: (String) -> Unit,
    uiState: LatestNewsUiState,
    pullRefresh: () -> Unit,
) {

    val lazyListState = rememberLazyListState()
    val enterAlwaysScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val scrollBehavior = remember { enterAlwaysScrollBehavior }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isRefreshingAll,
        onRefresh = {
            pullRefresh()
        }
    )

    LatestNewsScreen(
        uiState = uiState,
        lazyListState = lazyListState,
        navigateUp = { navigateUp() },
        onArticleClick = { id -> navigateToDetails(id) },
        scrollBehavior = scrollBehavior,
        pullRefreshState = pullRefreshState,
        modifier = modifier,
    )
}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class
)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LatestNewsScreen(
    uiState: LatestNewsUiState,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    onArticleClick: (String) -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior,
    pullRefreshState: PullRefreshState,
) {
    Box(
        modifier = modifier
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(
            state = lazyListState,
            modifier = modifier
                .fillMaxSize()
        ) {
            item {
                SmallAppBar(
                    title = uiState.title,
                    navigationIcon = { navigateUp() },
                    scrollBehavior = scrollBehavior,
                    modifier = Modifier.animateItemPlacement()
                )
            }

            if (uiState.isRefreshingAll) {
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .fillParentMaxHeight(0.75f)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.animateItemPlacement()
                        )
                    }
                }
            }


            if (uiState.latestNews?.results.isNullOrEmpty()) {
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .fillParentMaxHeight(0.75f)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.animateItemPlacement()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_results_not_found),
                                contentDescription = null,
                                alignment = Alignment.Center,
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                                modifier = Modifier
                                    .size(150.dp)
                            )
                            Text(
                                text = stringResource(R.string.nothing_found),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
            } else {
                uiState.latestNews?.results?.size?.let { size ->
                    items(size) { index ->
                        NewsSectionsCard(
                            title = uiState.latestNews!!.results[index].webTitle,
                            trailText = uiState.latestNews!!.results[index].trailText!!,
                            backgroundImageUrl = uiState.latestNews!!.results[index].thumbnail!!,
                            date = uiState.latestNews!!.results[index].webPublicationDate,
                            id = uiState.latestNews!!.results[index].id,
                            onClick = { id ->
                                onArticleClick(id)
                            },
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .animateItemPlacement()
                        )
                    }
                }
            }
        }

        PullRefreshIndicator(
            refreshing = uiState.isRefreshingAll,
            state = pullRefreshState,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun LatestNewsScreenPreview() {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = true,
        onRefresh = { })

    RightNewsComposeTheme {
        LatestNewsScreen(
            uiState = LatestNewsUiState(),
            lazyListState = rememberLazyListState(),
            navigateUp = {},
            scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
            pullRefreshState = pullRefreshState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DarkLatestNewsScreenPreview() {

    val pullRefreshState = rememberPullRefreshState(
        refreshing = true,
        onRefresh = { })

    RightNewsComposeTheme {
        LatestNewsScreen(
            uiState = LatestNewsUiState(),
            lazyListState = rememberLazyListState(),
            navigateUp = {},
            scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
            pullRefreshState = pullRefreshState
        )
    }
}