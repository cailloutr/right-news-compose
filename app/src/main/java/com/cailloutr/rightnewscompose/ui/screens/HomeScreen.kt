package com.cailloutr.rightnewscompose.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cailloutr.rightnewscompose.R
import com.cailloutr.rightnewscompose.ui.BottomNavigationBar
import com.cailloutr.rightnewscompose.navigation.HomeNavGraph
import com.cailloutr.rightnewscompose.ui.RightNewsMainAppbar
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        topBar = { RightNewsMainAppbar(title = stringResource(id = R.string.app_name)) },
        bottomBar = { BottomNavigationBar(navController = navController) },
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            HomeNavGraph(
                navController = navController
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    RightNewsComposeTheme {
        HomeScreen()
    }
}