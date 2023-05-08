package com.cailloutr.rightnewscompose.ui.screens.allsectionsscreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cailloutr.rightnewscompose.extensions.getNumberOfRows
import com.cailloutr.rightnewscompose.model.Section
import com.cailloutr.rightnewscompose.model.SectionsByIndex
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme
import com.cailloutr.rightnewscompose.ui.uistate.AllSectionsUiState

@Composable
fun AllSectionsScreen(
    modifier: Modifier = Modifier,
    uiState: AllSectionsUiState,
) {

    val lazyListState = rememberLazyListState()

    AllSectionsScreen(
        uiState = uiState,
        lazyListState = lazyListState,
        modifier = modifier
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AllSectionsScreen(
    modifier: Modifier = Modifier,
    uiState: AllSectionsUiState,
    lazyListState: LazyListState,
) {
    val list = uiState.sectionsByIndex

    LazyColumn(
        state = lazyListState,
        modifier = modifier
            .fillMaxSize()
    ) {
        items(list.size) { sectionIndex ->
            val gridRows = list[sectionIndex].list.getNumberOfRows()

            SectionsByIndex(
                rows = gridRows,
                lazyStaggeredGridState = rememberLazyStaggeredGridState(),
                contentPadding = 8f,
                section = list[sectionIndex],
                onClick = {
                    //TODO: Navigate to LatestNewsScreen
                },
                modifier = Modifier
                    .padding(vertical = 8.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AllSectionsScreenPreview() {
    val lazyListState = rememberLazyListState()

    RightNewsComposeTheme {
        AllSectionsScreen(
            uiState = AllSectionsUiState(
                sectionsByIndex = List(5) {
                    SectionsByIndex(
                        index = it.toString(),
                        List(5) { index ->
                            Section(
                                id = index.toString(),
                                title = index.toString(),
                                webUrl = index.toString(),
                                apiUrl = index.toString(),
                                code = index.toString()
                            )
                        }
                    )
                }
            ),
            lazyListState = lazyListState,
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DarkAllSectionsScreenPreview() {
    val lazyListState = rememberLazyListState()

    RightNewsComposeTheme {
        AllSectionsScreen(
            uiState = AllSectionsUiState(
                sectionsByIndex = List(5) {
                    SectionsByIndex(
                        index = it.toString(),
                        List(5) { index ->
                            Section(
                                id = index.toString(),
                                title = index.toString(),
                                webUrl = index.toString(),
                                apiUrl = index.toString(),
                                code = index.toString()
                            )
                        }
                    )
                }
            ),
            lazyListState = lazyListState,
        )
    }
}