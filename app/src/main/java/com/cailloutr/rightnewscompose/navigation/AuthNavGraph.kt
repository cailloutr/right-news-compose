package com.cailloutr.rightnewscompose.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.cailloutr.rightnewscompose.model.User
import com.cailloutr.rightnewscompose.ui.screens.loginscreen.LoginScreen
import com.cailloutr.rightnewscompose.ui.screens.loginscreen.SignInScreen
import com.cailloutr.rightnewscompose.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun NavGraphBuilder.authNavGraph(
    navigationActions: RightNewsNavigationActions,
    authViewModel: AuthViewModel,
) {
    navigation(
        route = Graph.LOGIN,
        startDestination = LoginScreen.Login.route
    ) {
        composable(route = LoginScreen.Login.route) {
            val uiState by authViewModel.uiState.collectAsStateWithLifecycle()

            val snackbarHostState = remember {
                SnackbarHostState()
            }

            LaunchedEffect(key1 = uiState.isLoggedIn) {
                if (uiState.isLoggedIn) {
                    navigationActions.navigateToMainScreen()
                }
            }

            LoginScreen(
                navigateToSignUpScreen = {
                    navigationActions.navigateToSignIn()
                },
                navigateToHomeScreen = { navigationActions.navigateToMainScreen() },
                uiState = uiState,
                onEmailValueChange = { email ->
                    authViewModel.setEmail(email)
                },
                onPasswordValueChange = { password ->
                    authViewModel.setPassword(password)
                },
                login = {
                    resetLoginErrorState(authViewModel)

                    when {
                        uiState.email.isEmpty() -> {
                            authViewModel.apply {
                                setEmailError(true)
                                setEmailSupportingText("This field cannot be empty")
                            }
                        }

                        uiState.password.isEmpty() -> {
                            authViewModel.apply {
                                setPasswordError(true)
                                setPasswordSupportingText("This field cannot be empty")
                            }
                        }

                        else -> {
                            authViewModel.login(
                                User(
                                    email = uiState.email,
                                    password = uiState.password
                                )
                            )
                        }
                    }
                },
                snackbarHostState = snackbarHostState,
            )
        }
        composable(route = LoginScreen.SignIn.route) {
            val uiState by authViewModel.signInUiState.collectAsStateWithLifecycle()
            val loginUiState by authViewModel.uiState.collectAsStateWithLifecycle()

            val scope = rememberCoroutineScope()

            LaunchedEffect(key1 = uiState.hasSignedInSuccessfully) {
                if (uiState.hasSignedInSuccessfully) {
                    authViewModel.login(
                        User(
                            email = uiState.email,
                            password = uiState.password,
                            phone = uiState.phone
                        )
                    )
                }
            }

            LaunchedEffect(key1 = loginUiState.isLoggedIn) {
                if (loginUiState.isLoggedIn) {
                    navigationActions.navigateToMainScreen()
                }
            }

            SignInScreen(
                uiState = uiState,
                onEmailValueChange = { value ->
                    authViewModel.setSignInEmailUiState(value)
                },
                onPasswordValueChange = { value ->
                    scope.launch {
                        authViewModel.setSignInPasswordUiState(value)

                        delay(200)

                        if (uiState.password.length < 6) {
                            authViewModel.apply {
                                setSingInPasswordErrorUiState(true)
                                setSignInPasswordSupportingTextUiState("It must have at least 6 numbers")
                            }
                        } else {
                            authViewModel.apply {
                                setSingInPasswordErrorUiState(false)
                                setSignInPasswordSupportingTextUiState("")
                            }
                        }
                    }
                },
                onPhoneValueChange = { value ->
                    authViewModel.setSignInPhoneUiState(value)
                },
                onConfirmPasswordValueChange = { value ->
                    scope.launch {
                        authViewModel.setSingInConfirmPasswordUiState(value)

                        delay(200)

                        if (uiState.confirmPassword != uiState.password) {
                            authViewModel.apply {
                                setSignInConfirmPasswordErrorUiState(true)
                                setSignInConfirmPasswordSupportingTextUiState("Password don't match")
                            }
                        } else {
                            authViewModel.apply {
                                setSignInConfirmPasswordErrorUiState(false)
                                setSignInConfirmPasswordSupportingTextUiState("")
                            }
                        }
                    }
                },
                navigateUp = {
                    navigationActions.navigateUp()
                },
                signIn = {
                    authViewModel.apply {
                        setSignInPhoneErrorUiState(false)
                        setSignInPhoneSupportingTextUiState("")
                        setSignInEmailErrorUiState(false)
                        setSignInEmailSupportingTextUiState("")
                        setSingInPasswordErrorUiState(false)
                        setSignInPasswordSupportingTextUiState("")
                        setSignInConfirmPasswordErrorUiState(false)
                        setSignInConfirmPasswordSupportingTextUiState("")
                    }

                    val emptyFieldMessage = "This field cannot be empty"

                    when {
                        uiState.phone.isEmpty() -> {
                            authViewModel.apply {
                                setSignInPhoneErrorUiState(true)
                                setSignInPhoneSupportingTextUiState(emptyFieldMessage)
                            }
                        }

                        uiState.email.isEmpty() -> {
                            authViewModel.apply {
                                setSignInEmailErrorUiState(true)
                                setSignInEmailSupportingTextUiState(emptyFieldMessage)
                            }
                        }

                        uiState.password.isEmpty() -> {
                            authViewModel.apply {
                                setSignInPasswordErrorUiState(true)
                                setSignInPasswordSupportingTextUiState(emptyFieldMessage)
                            }
                        }

                        uiState.confirmPassword.isEmpty() -> {
                            authViewModel.apply {
                                setSignInConfirmPasswordErrorUiState(true)
                                setSignInConfirmPasswordSupportingTextUiState(emptyFieldMessage)
                            }
                        }

                        else -> {
                            authViewModel.signIn(
                                User(
                                    email = uiState.email,
                                    password = uiState.password,
                                    phone = uiState.phone
                                )
                            )
                        }
                    }
                }
            )
        }
    }
}


private fun resetLoginErrorState(authViewModel: AuthViewModel) {
    authViewModel.apply {
        setEmailError(false)
        setEmailSupportingText("")
        setPasswordError(false)
        setPasswordSupportingText("")
    }
}