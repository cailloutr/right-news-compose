package com.cailloutr.rightnewscompose.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cailloutr.rightnewscompose.R
import com.cailloutr.rightnewscompose.ui.BottomNavigationBar
import com.cailloutr.rightnewscompose.navigation.HomeNavGraph
import com.cailloutr.rightnewscompose.ui.components.RightNewsMainAppbar
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    val appBarState = rememberTopAppBarState()
    val enterAlwaysScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(appBarState)
    val scrollBehavior = remember { enterAlwaysScrollBehavior }

    Scaffold(
        topBar = {
            RightNewsMainAppbar(
                title = stringResource(id = R.string.app_name),
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = { BottomNavigationBar(navController = navController) },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
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