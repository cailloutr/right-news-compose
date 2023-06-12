package com.cailloutr.rightnewscompose.ui.screens.loginscreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navigateToSignUpScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit,
) {

    var email by remember() {
        mutableStateOf("")
    }

    var password by remember() {
        mutableStateOf("")
    }

    val emailSupportingText by remember() {
        mutableStateOf("")
    }

    val passwordSupportingText by remember() {
        mutableStateOf("")
    }

    var passwordVisible by remember() {
        mutableStateOf(false)
    }

    val isError by remember {
        mutableStateOf(false)
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    val focusManager = LocalFocusManager.current

    LoginScreen(
        email = email,
        password = password,
        emailSupportingText = emailSupportingText,
        passwordSupportingText = passwordSupportingText,
        passwordVisible = passwordVisible,
        onPasswordVisibilityChange = {
            passwordVisible = !passwordVisible
        },
        onEmailValueChange = { value ->
            email = value
        },
        onPasswordValueChange = { value ->
            password = value
        },
        keyboardActionOnNext = {
            focusManager.moveFocus(FocusDirection.Down)
        },
        keyboardActionOnDone = {
            focusManager.clearFocus()
        },
        login = { _, _ ->

        },
        navigateToSignUpScreen = {
            navigateToSignUpScreen()
        },

        navigateToHomeScreen = { navigateToHomeScreen() },
        keyboardController = keyboardController,
        isError = isError
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    email: String = "",
    password: String = "",
    emailSupportingText: String = "",
    passwordSupportingText: String = "",
    passwordVisible: Boolean = false,
    onPasswordVisibilityChange: (Boolean) -> Unit = {},
    onEmailValueChange: (String) -> Unit = {},
    onPasswordValueChange: (String) -> Unit = {},
    keyboardActionOnNext: () -> Unit = {},
    keyboardActionOnDone: () -> Unit = {},
    login: (String, String) -> Unit = { _, _ -> },
    navigateToSignUpScreen: () -> Unit = {},
    navigateToHomeScreen: () -> Unit = {},
    keyboardController: SoftwareKeyboardController?,
    isError: Boolean = false,
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
            value = email,
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
            isError = isError,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
            ),
            supportingText = {
                Text(text = emailSupportingText)
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.size(26.dp))

        TextField(
            value = password,
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
                val description = if (passwordVisible) "Hide password" else "Show password"

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
            isError = isError,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
            ),
            supportingText = {
                Text(text = passwordSupportingText)
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.size(26.dp))

        Button(
            onClick = { login(email, password) },
            modifier = Modifier
                .width(200.dp)
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
            LoginScreen(keyboardController = keyboardController)
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
            LoginScreen(keyboardController = keyboardController)
        }
    }
}