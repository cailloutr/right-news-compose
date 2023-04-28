package com.cailloutr.rightnewscompose.ui.screens.mainscreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cailloutr.rightnewscompose.model.Article
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme
import com.ireward.htmlcompose.HtmlText

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BannerHorizontalPager(
    articleList: List<Article>,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState()
    val fling = PagerDefaults.flingBehavior(
        state = pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(10)
    )

    Column(modifier = modifier) {
        HorizontalPager(
            pageCount = articleList.size,
            pageSpacing = 16.dp,
            state = pagerState,
            flingBehavior = fling
        ) { page ->
            NewsBannerCard(
                title = articleList[page].webTitle,
                trailText = articleList[page].trailText.toString(),
                backgroundImageUrl = articleList[page].thumbnail.toString()
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Row(
            Modifier
                .height(18.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            repeat(articleList.size) { iteration ->
                val color = if (pagerState.currentPage == iteration)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.primaryContainer
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(18.dp)
                )
            }
        }
    }
}

@Composable
fun NewsBannerCard(
    title: String,
    trailText: String,
    backgroundImageUrl: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .height(250.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(backgroundImageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.size(25.dp))

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                    ) {
                        HtmlText(
                            text = trailText,
                            style = TextStyle.Default.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                                fontWeight = FontWeight.Bold
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun NewsBannerCardPreview() {
    RightNewsComposeTheme {
        Surface {
            NewsBannerCard(
                title = "Title",
                trailText = "Description",
                backgroundImageUrl = "https://cdn.pixabay.com/photo/2023/04/06/01/26/heart-7902540_960_720.jpg"
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DarkNewsBannerCardPreview() {
    RightNewsComposeTheme {
        Surface {
            NewsBannerCard(
                title = "Title",
                trailText = "Description",
                backgroundImageUrl = "https://cdn.pixabay.com/photo/2023/04/06/01/26/heart-7902540_960_720.jpg"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BannerHorizontalPagerPreview() {
    RightNewsComposeTheme {
        Surface {
            BannerHorizontalPager(
                articleList = List(5) {
                    Article(
                        id = it.toString(),
                        type = it.toString(),
                        sectionId = it.toString(),
                        sectionName = it.toString(),
                        webPublicationDate = it.toString(),
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
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DarkBannerHorizontalPagerPreview() {
    RightNewsComposeTheme {
        Surface {
            BannerHorizontalPager(
                articleList = List(5) {
                    Article(
                        id = it.toString(),
                        type = it.toString(),
                        sectionId = it.toString(),
                        sectionName = it.toString(),
                        webPublicationDate = it.toString(),
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
    }
}