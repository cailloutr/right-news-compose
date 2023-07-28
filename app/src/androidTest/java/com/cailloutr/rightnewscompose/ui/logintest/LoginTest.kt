package com.cailloutr.rightnewscompose.ui.logintest

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.test.printToLog
import androidx.test.filters.LargeTest
import com.cailloutr.rightnewscompose.ui.screens.loginscreen.LoginScreen
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme
import com.cailloutr.rightnewscompose.ui.uistate.LoginScreenUiState
import org.junit.Rule
import org.junit.Test

@LargeTest
class LoginTest {


    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun testLoginFlow() {
        composeTestRule.setContent {
            RightNewsComposeTheme {

                val snackbarHostState = remember {
                    SnackbarHostState()
                }

                var email by remember {
                    mutableStateOf("")
                }

                val emailSupportingText by remember {
                    mutableStateOf("")
                }

                val emailError by remember {
                    mutableStateOf(false)
                }

                var password by remember {
                    mutableStateOf("")
                }

                val passwordSupportingText by remember {
                    mutableStateOf("")
                }

                val passwordError by remember {
                    mutableStateOf(false)
                }

                LoginScreen(
                    navigateToSignUpScreen = {  },
                    navigateToHomeScreen = {  },
                    onEmailValueChange = { value ->
                        email = value
                    },
                    onPasswordValueChange = { value ->
                        password = value
                    },
                    login = {},
                    snackbarHostState = snackbarHostState,
                    uiState = LoginScreenUiState(
                        email = email,
                        password = password,
                        emailSupportingText = emailSupportingText,
                        emailError = emailError,
                        passwordSupportingText = passwordSupportingText,
                        passwordError = passwordError,
                        isLoggedIn = false,
                        snackbarHostState = false,
                        snackbarMessage = ""
                    )
                )
            }
        }

        composeTestRule.onNodeWithText("Email").performClick()
        composeTestRule.onNodeWithText("Email").performTextReplacement("caio.trocilo@gmail.com")
        composeTestRule.onNodeWithText("Email").performImeAction()

        composeTestRule.onNodeWithText("Password").performClick()
        composeTestRule.onNodeWithText("Password").performTextReplacement("123456")
        composeTestRule.onNodeWithText("Password").performImeAction()

        composeTestRule.onRoot(useUnmergedTree = true).printToLog("currentLabelExists")

        composeTestRule.onNodeWithTag("login_button").assertIsDisplayed()

    }
}