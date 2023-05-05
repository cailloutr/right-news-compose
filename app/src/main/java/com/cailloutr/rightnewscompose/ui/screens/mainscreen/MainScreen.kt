package com.cailloutr.rightnewscompose.ui.screens.mainscreen

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cailloutr.rightnewscompose.R
import com.cailloutr.rightnewscompose.model.Article
import com.cailloutr.rightnewscompose.model.ChipItem
import com.cailloutr.rightnewscompose.model.toChipItem
import com.cailloutr.rightnewscompose.other.Status
import com.cailloutr.rightnewscompose.ui.components.SearchBar
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme
import com.cailloutr.rightnewscompose.ui.viewmodel.NewsViewModel
import com.cailloutr.rightnewscompose.util.DateUtil
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navigateToDetails: (String) -> Unit,
    snackbarHostState: SnackbarHostState,
    context: Context,
    viewModel: NewsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isRefreshingAll,
        onRefresh = {
            viewModel.refreshData() { response ->
                val message: Int? = when (response.status) {
                    Status.ERROR -> { R.string.network_connection_error }
                    Status.SUCCESS -> { R.string.up_to_date }
                    else -> {null}
                }

                scope.launch {
                    message?.let {
                        snackbarHostState.showSnackbar(context.getString(it))
                    }
                }
            }
        }
    )

    MainScreen(
        latestNewsState = uiState.latestNews?.results ?: listOf(),
        sectionNewsState = uiState.sectionArticles?.results ?: listOf(),
        mainSectionsState = uiState.sections.map { it.toChipItem() },
        isRefreshingSectionsNewsState = uiState.isRefreshingSectionArticles,
        isRefreshingAll = uiState.isRefreshingAll,
        onSectionSelectedListener = { id ->
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
        },
        onArticleClickListener = { id ->
            navigateToDetails(id)
        },
        selectedSection = uiState.selectedSection,
        pullRefreshState = pullRefreshState,
        modifier = modifier
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    latestNewsState: List<Article>,
    sectionNewsState: List<Article>,
    mainSectionsState: List<ChipItem>,
    isRefreshingSectionsNewsState: Boolean,
    modifier: Modifier = Modifier,
    selectedSection: String = "",
    seeAllOnClick: () -> Unit = {},
    onSectionSelectedListener: (String) -> Unit = {},
    onArticleClickListener: (String) -> Unit = {},
    pullRefreshState: PullRefreshState,
    isRefreshingAll: Boolean,
) {
    Box(
        modifier = modifier
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(
            modifier = modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
        ) {
            item {
                SearchBar(
                    onValueChange = {},
                    onSearch = {},
                    enabled = false
                )
                Spacer(modifier = Modifier.size(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Latest News",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .weight(1f)
                            .alignByBaseline()
                    )

                    Row(
                        modifier = Modifier
                            .align(Alignment.Bottom)
                            .clickable {
                                seeAllOnClick()
                            }
                    ) {
                        Text(
                            text = "See all",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier
                                .size(16.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
                Spacer(modifier = Modifier.size(16.dp))
                BannerHorizontalPager(
                    articleList = latestNewsState,
                    onClickListener = { id ->
                        onArticleClickListener(id)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                SectionChipGroup(
                    list = mainSectionsState,
                    selectedSection = selectedSection,
                    onItemSelectedListener = { id ->
                        onSectionSelectedListener(id)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clipToBounds()
                )
                Spacer(modifier = Modifier.size(8.dp))
                if (isRefreshingSectionsNewsState) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CircularProgressIndicator(modifier = Modifier.animateItemPlacement())
                    }
                }
            }

            items(sectionNewsState) { article ->
                NewsSectionsCard(
                    title = article.webTitle,
                    trailText = article.trailText!!,
                    backgroundImageUrl = article.thumbnail!!,
                    date = DateUtil.getFormattedDate(article.webPublicationDate),
                    id = article.id,
                    onClick = { id ->
                        onArticleClickListener(id)
                    },
                    modifier = Modifier.animateItemPlacement()
                )
            }
        }

        PullRefreshIndicator(
            refreshing = isRefreshingAll,
            state = pullRefreshState,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val articleList by remember {
        mutableStateOf(
            List(5) {
                Article(
                    id = it.toString(),
                    type = it.toString(),
                    sectionId = it.toString(),
                    sectionName = it.toString(),
                    webPublicationDate = "2023-04-26T14:23:37Z",
                    webTitle = it.toString(),
                    webUrl = it.toString(),
                    apiUrl = it.toString(),
                    isHosted = false,
                    pillarId = it.toString(),
                    trailText = it.toString(),
                    pillarName = it.toString(),
                    thumbnail = it.toString(),
                    headline = it.toString(),
                    body = it.toString(),
                )
            }
        )
    }
    val sectionsList by remember {
        mutableStateOf(
            List(7) {
                ChipItem(
                    id = it.toString(),
                    text = it.toString(),
                )
            }
        )
    }
    val articles by remember {
        mutableStateOf(
            List(10) {
                Article(
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    "2023-04-26T14:23:37Z",
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    false,
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    it.toString()
                )
            }
        )
    }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = true,
        onRefresh = {  })

    RightNewsComposeTheme {
        Surface {
            MainScreen(
                latestNewsState = articleList,
                sectionNewsState = articles,
                mainSectionsState = sectionsList,
                isRefreshingSectionsNewsState = false,
                pullRefreshState = pullRefreshState,
                isRefreshingAll = true,
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DarkMainScreenPreview() {
    val articleList by remember {
        mutableStateOf(
            List(5) {
                Article(
                    id = it.toString(),
                    type = it.toString(),
                    sectionId = it.toString(),
                    sectionName = it.toString(),
                    webPublicationDate = "2023-04-26T14:23:37Z",
                    webTitle = it.toString(),
                    webUrl = it.toString(),
                    apiUrl = it.toString(),
                    isHosted = false,
                    pillarId = it.toString(),
                    trailText = it.toString(),
                    pillarName = it.toString(),
                    thumbnail = it.toString(),
                    headline = it.toString(),
                    body = it.toString(),
                )
            }
        )
    }
    val sectionsList by remember {
        mutableStateOf(
            List(7) {
                ChipItem(
                    id = it.toString(),
                    text = it.toString(),
                )
            }
        )
    }
    val articles by remember {
        mutableStateOf(
            List(10) {
                Article(
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    "2023-04-26T14:23:37Z",
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    false,
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    it.toString()
                )
            }
        )
    }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = true,
        onRefresh = {  })
    RightNewsComposeTheme {
        Surface {
            MainScreen(
                latestNewsState = articleList,
                sectionNewsState = articles,
                mainSectionsState = sectionsList,
                isRefreshingSectionsNewsState = false,
                pullRefreshState = pullRefreshState,
                isRefreshingAll = true,
            )
        }
    }
}