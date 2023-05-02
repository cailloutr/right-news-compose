package com.cailloutr.rightnewscompose.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cailloutr.rightnewscompose.navigation.BottomBarScreens
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    val screens = listOf(
        BottomBarScreens.Main,
        BottomBarScreens.Favorite,
        BottomBarScreens.Profile
    )

    NavigationBar(modifier = modifier) {

        screens.forEach { screen ->
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
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        BottomNavigationBar(
            navController = navController,
            currentDestination = currentDestination,
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DarkBottomNavigationBar() {
    RightNewsComposeTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        BottomNavigationBar(
            navController = navController,
            currentDestination = currentDestination,
        )
    }
}