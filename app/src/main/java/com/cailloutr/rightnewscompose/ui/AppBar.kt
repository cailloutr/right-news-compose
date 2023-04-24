package com.cailloutr.rightnewscompose.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme

@Composable
fun RightNewsMainAppbar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
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
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun RightNewsAppbarPreview() {
    RightNewsComposeTheme {
        Surface {
            RightNewsMainAppbar(title = "Right News")
        }
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DarkRightNewsAppbarPreview() {
    RightNewsComposeTheme {
        Surface {
            RightNewsMainAppbar(title = "Right News")
        }
    }
}