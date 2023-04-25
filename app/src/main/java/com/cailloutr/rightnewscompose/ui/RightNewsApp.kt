package com.cailloutr.rightnewscompose.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.cailloutr.rightnewscompose.navigation.RootNavGraph
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme

@Composable
fun RightNewsApp() {
    RootNavGraph()
}

@Preview(showBackground = true)
@Composable
fun RightNewsAppPreview() {
    RightNewsComposeTheme {
        RightNewsApp()
    }
}