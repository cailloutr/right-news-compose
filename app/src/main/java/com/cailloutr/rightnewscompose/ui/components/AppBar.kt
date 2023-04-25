package com.cailloutr.rightnewscompose.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RightNewsMainAppbar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = title)
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        navigationIcon = { navigationIcon() },
        actions = { actions() },
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun RightNewsAppbarPreview() {
    val appBarState = rememberTopAppBarState()
    val enterAlwaysScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(appBarState)
    val scrollBehavior = remember { enterAlwaysScrollBehavior }
    RightNewsComposeTheme {
        Surface {
            RightNewsMainAppbar(title = "Right News", scrollBehavior = scrollBehavior)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DarkRightNewsAppbarPreview() {
    val appBarState = rememberTopAppBarState()
    val enterAlwaysScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(appBarState)
    val scrollBehavior = remember { enterAlwaysScrollBehavior }
    RightNewsComposeTheme {
        Surface {
            RightNewsMainAppbar(title = "Right News", scrollBehavior = scrollBehavior)
        }
    }
}