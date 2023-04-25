package com.cailloutr.rightnewscompose.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cailloutr.rightnewscompose.HomeScreens
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme

@Composable
fun BottomNavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val bottomDestinations = listOf(
        HomeScreens.MainScreen,
        HomeScreens.FavoriteScreen,
        HomeScreens.ProfileScreen
    )
    NavigationBar(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        bottomDestinations.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                icon = {
                    screen.drawableRes?.let {
                        Icon(
                            painter = painterResource(id = screen.drawableRes),
                            contentDescription = null
                        )
                    }
                },
                label = {
                    Text(text = stringResource(id = screen.stringRes))
                },
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBar() {
    RightNewsComposeTheme {
        val navController = rememberNavController()
        BottomNavigationBar(navController = navController)
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DarkBottomNavigationBar() {
    RightNewsComposeTheme {
        val navController = rememberNavController()
        Surface {
            BottomNavigationBar(navController = navController)
        }
    }
}