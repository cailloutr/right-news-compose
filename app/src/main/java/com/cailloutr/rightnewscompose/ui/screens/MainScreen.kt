package com.cailloutr.rightnewscompose.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.cailloutr.rightnewscompose.model.Article
import com.cailloutr.rightnewscompose.model.Banner
import com.cailloutr.rightnewscompose.model.ChipItem
import com.cailloutr.rightnewscompose.ui.BannerHorizontalPager
import com.cailloutr.rightnewscompose.ui.NewsSectionsCard
import com.cailloutr.rightnewscompose.ui.SearchBar
import com.cailloutr.rightnewscompose.ui.SectionChipGroup
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme


@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {

    val bannerState by remember {
        mutableStateOf<List<Banner>>(listOf())
    }

    val sectionNewsState by remember {
        mutableStateOf<List<Article>>(listOf())
    }

    val mainSectionsState by remember {
        mutableStateOf<List<ChipItem>>(listOf())
    }

    val isRefreshingSectionsNewsState by remember {
        mutableStateOf(false)
    }

    MainScreen(
        bannerState = bannerState,
        sectionNewsState = sectionNewsState,
        mainSectionsState = mainSectionsState,
        isRefreshingSectionsNewsState = isRefreshingSectionsNewsState,
        modifier = modifier
    )
}

@Composable
fun MainScreen(
    bannerState: List<Banner>,
    sectionNewsState: List<Article>,
    mainSectionsState: List<ChipItem>,
    isRefreshingSectionsNewsState: Boolean,
    modifier: Modifier = Modifier,
    selectedSection: String = ""

) {
    LazyColumn(
        modifier = modifier
            .padding(16.dp)
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
                            TODO()
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
                bannerList = bannerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            SectionChipGroup(
                list = mainSectionsState,
                selectedSection = selectedSection,
                onItemSelectedListener = {},
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
                    CircularProgressIndicator()
                }
            }
        }

        items(sectionNewsState) { article ->
            NewsSectionsCard(
                title = article.webTitle,
                trailText = article.trailText!!,
                backgroundImageUrl = article.thumbnail!!,
                date = article.webPublicationDate
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val bannerList by remember {
        mutableStateOf(
            List(5) {
                Banner(it.toLong(), it.toString(), it.toString(), it.toString())
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
                    it.toString(),
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
    val navControler = rememberNavController()
    RightNewsComposeTheme {
        Surface {
            MainScreen(
                bannerState = bannerList,
                mainSectionsState = sectionsList,
                sectionNewsState = articles,
                isRefreshingSectionsNewsState = false,
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DarkMainScreenPreview() {
    val bannerList by remember {
        mutableStateOf(
            List(5) {
                Banner(it.toLong(), it.toString(), it.toString(), it.toString())
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
    val navController = rememberNavController()
    val articles by remember {
        mutableStateOf(
            List(10) {
                Article(
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    it.toString(),
                    it.toString(),
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
    RightNewsComposeTheme {
        Surface {
            MainScreen(
                bannerState = bannerList,
                mainSectionsState = sectionsList,
                sectionNewsState = articles,
                isRefreshingSectionsNewsState = false,
            )
        }
    }
}