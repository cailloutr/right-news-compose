package com.cailloutr.rightnewscompose.ui.screens.searchscreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cailloutr.rightnewscompose.R
import com.cailloutr.rightnewscompose.other.Status
import com.cailloutr.rightnewscompose.ui.components.SearchBar
import com.cailloutr.rightnewscompose.ui.components.SmallAppBar
import com.cailloutr.rightnewscompose.ui.screens.mainscreen.NewsSectionsCard
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme
import com.cailloutr.rightnewscompose.ui.uistate.SearchNewsUiState

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun SearchScreen(
    uiState: SearchNewsUiState,
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
    navigateToDetails: (String) -> Unit,
    navigateUp: () -> Unit,
) {

    val lazyListState = rememberLazyListState()
    val enterAlwaysScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val scrollBehavior = remember { enterAlwaysScrollBehavior }
    val keyboardController = LocalSoftwareKeyboardController.current

    var searchBarValue by rememberSaveable {
        mutableStateOf("")
    }

    SearchScreen(
        uiState = uiState,
        modifier = modifier,
        lazyListState = lazyListState,
        scrollBehavior = scrollBehavior,
        navigateUp = { navigateUp() },
        searchBarOnValueChange = { newValue ->
            searchBarValue = newValue
        },
        searchBarValue = searchBarValue,
        onSearch = { query ->
            onSearch(query)
        },
        onArticleClick = { id ->
            navigateToDetails(id)
        },
        keyboardController = keyboardController
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class
)
@Composable
fun SearchScreen(
    uiState: SearchNewsUiState,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    scrollBehavior: TopAppBarScrollBehavior,
    navigateUp: () -> Unit,
    searchBarOnValueChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onArticleClick: (String) -> Unit,
    searchBarValue: String,
    keyboardController: SoftwareKeyboardController?,
) {
    LazyColumn(
        state = lazyListState,
        modifier = modifier.fillMaxSize()
    ) {
        item {
            SmallAppBar(
                title = stringResource(R.string.search_screen_title),
                navigationIcon = { navigateUp() },
                scrollBehavior = scrollBehavior,
                modifier = Modifier.animateItemPlacement()
            )
            Spacer(modifier = Modifier.size(8.dp))
            SearchBar(
                enabled = true,
                text = searchBarValue,
                onValueChange = { newValue ->
                    searchBarOnValueChange(newValue)
                },
                onSearch = { query ->
                    onSearch(query)
                },
                keyboardController = keyboardController,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
        }

        if (uiState.isRefreshingAll) {
            item {
                Box(
                    contentAlignment = Alignment.TopCenter,
                    modifier = Modifier
                        .fillParentMaxWidth()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .animateItemPlacement()
                            .padding(22.dp)
                    )
                }
            }
        } else {
            if ((uiState.latestNews?.results.isNullOrEmpty() || uiState.status == Status.ERROR)) {
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .fillParentMaxHeight(0.65f)
                            .animateItemPlacement()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.animateItemPlacement()
                        ) {
                            Image(
                                painter = if (uiState.latestNews == null)
                                    painterResource(id = R.drawable.ic_search_24)
                                else
                                    painterResource(id = R.drawable.ic_results_not_found),
                                contentDescription = null,
                                alignment = Alignment.Center,
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                                modifier = Modifier
                                    .size(150.dp)
                            )
                            Text(
                                text = if (uiState.latestNews == null)
                                    stringResource(R.string.search_content)
                                else
                                    stringResource(id = R.string.nothing_found),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
            } else {
                uiState.latestNews?.results?.size?.let { size ->
                    items(
                        count = size,
                        key = {
                            uiState.latestNews!!.results[it].id
                        }
                    ) { index ->
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
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class
)
@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    RightNewsComposeTheme {
        val lazyListState = rememberLazyListState()
        val enterAlwaysScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        val scrollBehavior = remember { enterAlwaysScrollBehavior }

        val searchBarValue = ""
        SearchScreen(
            uiState = SearchNewsUiState(),
            lazyListState = lazyListState,
            scrollBehavior = scrollBehavior,
            navigateUp = {},
            searchBarOnValueChange = {},
            onSearch = {},
            onArticleClick = {},
            searchBarValue = searchBarValue,
            keyboardController = LocalSoftwareKeyboardController.current
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class
)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DarkSearchScreenPreview() {
    RightNewsComposeTheme {
        val lazyListState = rememberLazyListState()
        val enterAlwaysScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        val scrollBehavior = remember { enterAlwaysScrollBehavior }

        val searchBarValue = ""
        SearchScreen(
            uiState = SearchNewsUiState(),
            lazyListState = lazyListState,
            scrollBehavior = scrollBehavior,
            navigateUp = {},
            searchBarOnValueChange = {},
            onSearch = {},
            onArticleClick = {},
            searchBarValue = searchBarValue,
            keyboardController = LocalSoftwareKeyboardController.current
        )
    }
}