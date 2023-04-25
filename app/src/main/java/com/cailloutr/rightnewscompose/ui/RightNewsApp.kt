package com.cailloutr.rightnewscompose.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.cailloutr.rightnewscompose.R
import com.cailloutr.rightnewscompose.ui.screens.MainScreen
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RightNewsApp() {
    val navController = rememberNavController()
    RightNewsComposeTheme {
        Scaffold(
            topBar = { RightNewsMainAppbar(title = stringResource(id = R.string.app_name)) },
            bottomBar = { BottomNavigationBar(navController = navController) },
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                MainScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RightNewsAppPreview() {
    RightNewsComposeTheme {
        RightNewsApp()
    }
}