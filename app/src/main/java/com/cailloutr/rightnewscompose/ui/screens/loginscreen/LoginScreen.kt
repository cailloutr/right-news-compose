package com.cailloutr.rightnewscompose.ui.screens.loginscreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cailloutr.rightnewscompose.R
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme
import com.cailloutr.rightnewscompose.ui.uistate.LoginScreenUiState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navigateToSignUpScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    onEmailValueChange: (String) -> Unit,
    onPasswordValueChange: (String) -> Unit,
    login: () -> Unit,
    snackbarHostState: SnackbarHostState,
    uiState: LoginScreenUiState,
) {

    var passwordVisible by remember() {
        mutableStateOf(false)
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    val focusManager = LocalFocusManager.current

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        modifier = modifier
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            LoginScreen(
                uiState = uiState,
                passwordVisible = passwordVisible,
                onPasswordVisibilityChange = {
                    passwordVisible = !passwordVisible
                },
                onEmailValueChange = { value ->
                    onEmailValueChange(value)
                },
                onPasswordValueChange = { value ->
                    onPasswordValueChange(value)
                },
                keyboardActionOnNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                },
                keyboardActionOnDone = {
                    focusManager.clearFocus()
                },
                login = { login() },
                navigateToSignUpScreen = {
                    navigateToSignUpScreen()
                },
                navigateToHomeScreen = { navigateToHomeScreen() },
                keyboardController = keyboardController,
                emailError = uiState.emailError,
                passwordError = uiState.passwordError,
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    uiState: LoginScreenUiState,
    passwordVisible: Boolean = false,
    onPasswordVisibilityChange: (Boolean) -> Unit = {},
    onEmailValueChange: (String) -> Unit = {},
    onPasswordValueChange: (String) -> Unit = {},
    keyboardActionOnNext: () -> Unit = {},
    keyboardActionOnDone: () -> Unit = {},
    login: () -> Unit = { },
    navigateToSignUpScreen: () -> Unit = {},
    navigateToHomeScreen: () -> Unit = {},
    keyboardController: SoftwareKeyboardController?,
    emailError: Boolean = false,
    passwordError: Boolean = false,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.size(90.dp))

        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.size(110.dp))

        Text(
            text = stringResource(id = R.string.login),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.size(30.dp))

        TextField(
            value = uiState.email,
            onValueChange = { email -> onEmailValueChange(email) },
            label = {
                Text(text = stringResource(R.string.email))
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = null)
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    keyboardActionOnNext()
                }
            ),
            isError = emailError,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
            ),
            supportingText = {
                Text(text = uiState.emailSupportingText)
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.size(26.dp))

        TextField(
            value = uiState.password,
            onValueChange = { password -> onPasswordValueChange(password) },
            label = {
                Text(text = stringResource(R.string.password))
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = null)
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    painterResource(id = R.drawable.ic_visibility_24)
                else painterResource(id = R.drawable.ic_visibility_off_24)

                // Description for accessibility services
                val description =
                    if (passwordVisible)
                        stringResource(R.string.hide_password)
                    else
                        stringResource(R.string.show_password)

                IconButton(onClick = { onPasswordVisibilityChange(passwordVisible) }) {
                    Icon(painter = image, description)
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardActionOnDone()
                    keyboardController?.hide()
                }
            ),
            isError = passwordError,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
            ),
            supportingText = {
                Text(text = uiState.passwordSupportingText)
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.size(26.dp))

        Button(
            onClick = {
                login()
            },
            modifier = Modifier
                .width(200.dp)
                .testTag("login_button")
        ) {
            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        Row() {
            Text(
                text = stringResource(R.string.dont_have_an_account),
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = stringResource(R.string.sign_up),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    navigateToSignUpScreen()
                }
            )
        }

        Spacer(modifier = Modifier.size(110.dp))

        Text(
            text = stringResource(R.string.back_to_news),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable {
                navigateToHomeScreen()
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val keyboardController = LocalSoftwareKeyboardController.current
    RightNewsComposeTheme {
        Surface {
            LoginScreen(keyboardController = keyboardController, uiState = LoginScreenUiState())
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DarkLoginScreenPreview() {
    val keyboardController = LocalSoftwareKeyboardController.current
    RightNewsComposeTheme {
        Surface {
            LoginScreen(keyboardController = keyboardController, uiState = LoginScreenUiState())
        }
    }
}