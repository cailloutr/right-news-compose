package com.cailloutr.rightnewscompose.ui.screens.detailscreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionLayoutState
import androidx.constraintlayout.compose.MotionScene
import androidx.constraintlayout.compose.layoutId
import androidx.constraintlayout.compose.rememberMotionLayoutState
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cailloutr.rightnewscompose.R
import com.cailloutr.rightnewscompose.ui.components.DropDownMenu
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme
import com.cailloutr.rightnewscompose.ui.uistate.DetailsScreenUiState
import com.cailloutr.rightnewscompose.util.DateUtil
import com.ireward.htmlcompose.HtmlText


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMotionApi::class)
@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    uiState: DetailsScreenUiState,
    navigateUp: () -> Unit,
    share: (String) -> Unit,
    openLink: (String) -> Unit,
) {
    val context = LocalContext.current
    val motionScene = rememberSaveable {
        context.resources
            .openRawResource(R.raw.detail_screen_motion_scene)
            .readBytes()
            .decodeToString()
    }

    /**
     * These three are for saving the progress of the animation in motionState to survive configuration changes
     *
     * */
    val motionState = rememberMotionLayoutState()
    var motionStateCurrentProgress by rememberSaveable {
        mutableStateOf(0f)
    }
    // Allows the execution of snapTo(motionStateCurrentProgress) only once per recomposition
    var initialCondition by remember {
        mutableStateOf(true)
    }

    val scrollState = rememberScrollState()
    val isScrollEnabled = rememberSaveable { mutableStateOf(false) }

    val dropDownItems = listOf(
        DropDownItem(text = R.string.share),
        DropDownItem(text = R.string.open_in_browser)
    )

    DetailsScreen(
        motionScene = motionScene,
        motionState = motionState,
        motionStateProgress = motionStateCurrentProgress,
        scrollState = scrollState,
        isScrollEnabled = isScrollEnabled,
        thumbnail = uiState.thumbnail,
        publicationDate = uiState.publicationDate,
        title = uiState.title,
        body = uiState.body,
        onNavigateIconClick = navigateUp,
        onMotionStateProgressChanges = {
            motionStateCurrentProgress = it
        },
        onShareOptionClick = {
            share(uiState.webUrl)
        },
        onOpenLinkClick = {
            openLink(uiState.webUrl)
        },
        initialCondition = initialCondition,
        onStart = {
            initialCondition = false
        },
        dropDownItems = dropDownItems,
        modifier = modifier
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMotionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    motionScene: String,
    motionState: MotionLayoutState,
    scrollState: ScrollState,
    isScrollEnabled: MutableState<Boolean>,
    thumbnail: String,
    publicationDate: String,
    title: String,
    body: String,
    onFavoriteClick: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false,
    initialCondition: Boolean,
    onNavigateIconClick: () -> Unit,
    onShareOptionClick: () -> Unit,
    onOpenLinkClick: () -> Unit,
    dropDownItems: List<DropDownItem>,
    onStart: () -> Unit,
    onMotionStateProgressChanges: (Float) -> Unit,
    motionStateProgress: Float = motionState.currentProgress,
) {
    val corners = 50f - ((motionState.currentProgress * 10)).coerceAtLeast(10f)


    LaunchedEffect(motionStateProgress) {
        if (initialCondition) {
            motionState.snapTo(motionStateProgress)
        }
    }

    LaunchedEffect(motionState.currentProgress) {
        when (motionState.currentProgress) {
            0f -> {
                isScrollEnabled.value = false
                onMotionStateProgressChanges(0f)
            }

            1f -> {
                isScrollEnabled.value = true
                onMotionStateProgressChanges(1f)
            }
        }
    }

    LaunchedEffect(initialCondition) {
        onStart()
    }

    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        motionLayoutState = motionState,
        modifier = modifier
            .fillMaxSize()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(thumbnail)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .layoutId("headerImage")
        )

        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxHeight()
                .background(
                    MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(topStart = corners, topEnd = corners)
                )
                .layoutId("contentBg")
        ) {
            AnimatedVisibility(visible = isScrollEnabled.value) {
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_down),
                    contentDescription = "Pull down header",
                    alignment = Alignment.Center,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary),
                    modifier = Modifier
                        .height(50.dp)
                        .widthIn(30.dp)
                        .clip(RoundedCornerShape(bottomStart = corners, bottomEnd = corners))
                        .background(MaterialTheme.colorScheme.secondary)
                )
            }
        }

        Column(
            modifier = Modifier
                .layoutId("contentText")
                .verticalScroll(
                    enabled = isScrollEnabled.value,
                    state = scrollState,
                )
        ) {
            HtmlText(
                text = body,
                style = TextStyle.Default.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontFamily = Font(
                        resId = R.font.nunito_regular
                    ).toFontFamily(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 24.sp,
                    letterSpacing = (0.5).sp,
                ),
                modifier = Modifier
                    .padding(
                        top = 80.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
            )
        }


        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.7f)
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp,
            ),
            modifier = Modifier
                .layoutId("card")

        ) {
            Text(
                style = MaterialTheme.typography.bodySmall,
                text = try {
                    DateUtil.getFormattedDate(publicationDate)
                } catch (e: Exception) {
                    publicationDate
                },
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 8.dp
                )
            )
            Text(
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                text = title,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            )
        }

        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f),
                contentColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.7f)
            ),
            onClick = {
                onNavigateIconClick()
            },
            modifier = Modifier
                .layoutId("navigateBack")
        ) {
            Image(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.navigate_back),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.7f)
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f))
            )
        }

        DropDownMenu(
            dropDownItems = dropDownItems,
            onClick = { menuItem ->
                when (menuItem.text) {
                    R.string.share -> {
                        onShareOptionClick()
                    }

                    R.string.open_in_browser -> {
                        onOpenLinkClick()
                    }
                }
            },
            modifier = Modifier.layoutId("more")
        )

        //TODO: animation
        ExtendedFloatingActionButton(
            text = {
                AnimatedVisibility(visible = isFavorite) {
                    Text(text = "Favorite")
                }
            },
            icon = {
                   Icon(
                       imageVector = Icons.Default.FavoriteBorder,
                       contentDescription = null
                   )
            },
            expanded = isFavorite,
            onClick = { /*TODO*/ },
            modifier = Modifier.layoutId("fab")
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun DetailsScreenPreview() {
    RightNewsComposeTheme {
        Surface {
            val uiState = DetailsScreenUiState(
                thumbnail = "",
                publicationDate = "25/12/1225",
                title = "Teste",
                body = "Teste",
            )
            DetailsScreen(
                uiState = uiState,
                navigateUp = {},
                share = {},
                openLink = {}
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DarkDetailsScreenPreview() {
    RightNewsComposeTheme {
        Surface {
            val uiState = DetailsScreenUiState(
                thumbnail = "",
                publicationDate = "25/12/1225",
                title = "Teste",
                body = "Teste",
            )
            DetailsScreen(
                uiState = uiState,
                navigateUp = {},
                share = {},
                openLink = {}
            )
        }
    }
}
