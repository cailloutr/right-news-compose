package com.cailloutr.rightnewscompose.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cailloutr.rightnewscompose.R
import com.cailloutr.rightnewscompose.navigation.AllSectionsScreen
import com.cailloutr.rightnewscompose.navigation.BottomBarScreens
import com.cailloutr.rightnewscompose.navigation.HomeNavGraph
import com.cailloutr.rightnewscompose.navigation.RightNewsNavigationActions
import com.cailloutr.rightnewscompose.ui.components.BottomNavigationBar
import com.cailloutr.rightnewscompose.ui.components.CenterAlignedTopAppBar
import com.cailloutr.rightnewscompose.ui.components.SmallAppBar
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    val appBarState = rememberTopAppBarState()
    val enterAlwaysScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(appBarState)
    val scrollBehavior = remember { enterAlwaysScrollBehavior }
    val navigationActions = RightNewsNavigationActions(navController)

    val screens = listOf(
        BottomBarScreens.Main,
        BottomBarScreens.Favorite,
        BottomBarScreens.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    val appBarDestination = screens.any { it.route == currentDestination?.route }
    val isAllSectionsDestination =
        currentDestination?.route == AllSectionsScreen.AllSections.route

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        topBar = {
            AnimatedVisibility(visible = appBarDestination) {
                CenterAlignedTopAppBar(
                    title = stringResource(id = R.string.app_name),
                    scrollBehavior = scrollBehavior
                )
            }
            AnimatedVisibility(visible = isAllSectionsDestination) {
                SmallAppBar(
                    title = stringResource(id = R.string.all_sections),
                    navigationIcon = { navigationActions.navigateUp() },
                    scrollBehavior = scrollBehavior
                )
            }
        },
        bottomBar = {
            AnimatedVisibility(visible = bottomBarDestination) {
                BottomNavigationBar(
                    navController = navController,
                    currentDestination = currentDestination,
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            HomeNavGraph(
                navController = navController,
                snackbarHostState = snackbarHostState
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun HomeScreenPreview() {
    RightNewsComposeTheme {
        HomeScreen()
    }
}