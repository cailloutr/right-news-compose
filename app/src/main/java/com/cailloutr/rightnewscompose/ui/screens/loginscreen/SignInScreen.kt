package com.cailloutr.rightnewscompose.ui.screens.loginscreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.cailloutr.rightnewscompose.ui.components.SmallAppBar
import com.cailloutr.rightnewscompose.ui.theme.RightNewsComposeTheme
import com.cailloutr.rightnewscompose.ui.uistate.SignInUiState
import com.cailloutr.rightnewscompose.util.PhoneNumberVisualTransformation

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SignInScreen(
    uiState: SignInUiState,
    onEmailValueChange: (String) -> Unit,
    onPasswordValueChange: (String) -> Unit,
    onPhoneValueChange: (String) -> Unit,
    onConfirmPasswordValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    signIn: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val appBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    var passwordVisible by remember() {
        mutableStateOf(false)
    }

    SignInScreen(
        scrollState = scrollState,
        appBarScrollBehavior = appBarScrollBehavior,
        keyboardController = keyboardController,
        navigateUp = {
            navigateUp()
        },
        modifier = modifier,
        email = uiState.email,
        onEmailValueChange = { value ->
            onEmailValueChange(value)
        },
        emailError = uiState.emailError,
        password = uiState.password,
        onPasswordValueChange = { value ->
            onPasswordValueChange(value)
        },
        passwordError = uiState.passwordError,
        passwordSupportingText = uiState.passwordSupportingText,
        confirmPassword = uiState.confirmPassword,
        onConfirmPasswordValueChange = { value ->
            onConfirmPasswordValueChange(value)
        },
        confirmPasswordError = uiState.confirmPasswordError,
        confirmPasswordSupportingText = uiState.confirmPasswordSupportingText,
        phone = uiState.phone,
        onPhoneValueChange = { value ->
            onPhoneValueChange(value)
        },
        phoneError = uiState.phoneError,
        emailSupportingText = uiState.emailSupportingText,
        phoneSupportingText = uiState.phoneSupportingText,
        passwordVisible = passwordVisible,
        onPasswordVisibilityChange = {
            passwordVisible = !passwordVisible
        },
        keyboardActionOnNext = {
            focusManager.moveFocus(FocusDirection.Down)
        },
        keyboardActionOnDone = {
            focusManager.clearFocus()
        },
        signIn = { signIn() }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SignInScreen(
    scrollState: ScrollState,
    appBarScrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    email: String = "",
    onEmailValueChange: (String) -> Unit = {},
    emailSupportingText: String = "",
    password: String = "",
    onPasswordValueChange: (String) -> Unit = {},
    passwordSupportingText: String = "",
    onPasswordVisibilityChange: (Boolean) -> Unit = {},
    passwordVisible: Boolean = false,
    confirmPassword: String = "",
    onConfirmPasswordValueChange: (String) -> Unit = {},
    confirmPasswordSupportingText: String = "",
    phone: String = "",
    onPhoneValueChange: (String) -> Unit = {},
    phoneError: Boolean = false,
    phoneSupportingText: String = "",
    keyboardActionOnNext: () -> Unit = {},
    keyboardActionOnDone: () -> Unit = {},
    keyboardController: SoftwareKeyboardController?,
    passwordError: Boolean = false,
    confirmPasswordError: Boolean = false,
    emailError: Boolean = false,
    signIn: () -> Unit,
) {
    Scaffold(
        topBar = {
            SmallAppBar(
                title = stringResource(id = R.string.sign_up),
                navigationIcon = { navigateUp() },
                scrollBehavior = appBarScrollBehavior
            )
        },
        snackbarHost = {},
        modifier = modifier
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            ) {

                TextField(
                    value = phone,
                    onValueChange = { phone -> onPhoneValueChange(phone) },
                    label = {
                        Text(text = stringResource(R.string.phone))
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Phone, contentDescription = null)
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            keyboardActionOnNext()
                        }
                    ),
                    isError = phoneError,
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                    ),
                    supportingText = {
                        Text(text = phoneSupportingText)
                    },
                    visualTransformation = PhoneNumberVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.size(16.dp))

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
                    isError = emailError,
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

                Spacer(modifier = Modifier.size(16.dp))

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
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Password
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            keyboardActionOnNext()
                        }
                    ),
                    isError = passwordError,
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

                Spacer(modifier = Modifier.size(16.dp))

                TextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword ->
                        onConfirmPasswordValueChange(
                            confirmPassword
                        )
                    },
                    label = {
                        Text(text = stringResource(R.string.confirm_password))
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
                    isError = confirmPasswordError,
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                    ),
                    supportingText = {
                        Text(text = confirmPasswordSupportingText)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.size(16.dp))

                Button(
                    onClick = {
                        signIn()
                    },
                    modifier = Modifier
                        .width(200.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.sign_up),
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Spacer(modifier = Modifier.size(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Divider(modifier = Modifier.width(150.dp))
                    Text(
                        text = "or",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Divider(modifier = Modifier.width(150.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    val scrollState = rememberScrollState()

    val appBarState = rememberTopAppBarState()
    val enterAlwaysScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(appBarState)
    val scrollBehavior = remember { enterAlwaysScrollBehavior }

    val keyboardController = LocalSoftwareKeyboardController.current

    RightNewsComposeTheme {
        Surface {
            SignInScreen(
                scrollState = scrollState,
                appBarScrollBehavior = scrollBehavior,
                phoneSupportingText = "",
                keyboardController = keyboardController,
                signIn = { }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DarkSignInScreenPreview() {
    val scrollState = rememberScrollState()

    val appBarState = rememberTopAppBarState()
    val enterAlwaysScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(appBarState)
    val scrollBehavior = remember { enterAlwaysScrollBehavior }

    val keyboardController = LocalSoftwareKeyboardController.current

    RightNewsComposeTheme {
        Surface {
            SignInScreen(
                scrollState = scrollState,
                appBarScrollBehavior = scrollBehavior,
                phoneSupportingText = "",
                keyboardController = keyboardController,
                signIn = {}
            )
        }
    }
}