package com.cailloutr.rightnewscompose.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.cailloutr.rightnewscompose.navigation.RootNavGraph
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RightNewsApp() {
    val navController = rememberNavController()
    RootNavGraph(navController = navController)
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun RightNewsAppPreview() {
    RightNewsComposeTheme {
        RightNewsApp()
    }
}