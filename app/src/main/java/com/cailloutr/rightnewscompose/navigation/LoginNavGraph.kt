package com.cailloutr.rightnewscompose.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.cailloutr.rightnewscompose.ui.screens.loginscreen.LoginScreen
import com.cailloutr.rightnewscompose.ui.screens.loginscreen.SignInScreen
import com.cailloutr.rightnewscompose.ui.viewmodel.LoginViewModel

fun NavGraphBuilder.loginNavGraph(
    navigationActions: RightNewsNavigationActions,
    viewModel: LoginViewModel,
) {
    navigation(
        route = Graph.LOGIN,
        startDestination = LoginScreen.Login.route
    ) {
        composable(route = LoginScreen.Login.route) {
            LoginScreen(
                navigateToSignUpScreen = {
                    navigationActions.navigateToSignIn()
                },
                navigateToHomeScreen = { navigationActions.navigateToMainScreen() }
            )
        }
        composable(route = LoginScreen.SignIn.route) {
            SignInScreen(navigateUp = navigationActions.navigateUp)
        }
    }
}