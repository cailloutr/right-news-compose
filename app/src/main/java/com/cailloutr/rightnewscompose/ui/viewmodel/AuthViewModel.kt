package com.cailloutr.rightnewscompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cailloutr.rightnewscompose.model.User
import com.cailloutr.rightnewscompose.other.DispatchersProvider
import com.cailloutr.rightnewscompose.other.Resource
import com.cailloutr.rightnewscompose.other.Status
import com.cailloutr.rightnewscompose.repository.AuthenticationRepositoryInterface
import com.cailloutr.rightnewscompose.ui.uistate.LoginScreenUiState
import com.cailloutr.rightnewscompose.ui.uistate.ProfileScreenUiState
import com.cailloutr.rightnewscompose.ui.uistate.SignInUiState
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val dispatchers: DispatchersProvider,
    private val authenticationRepository: AuthenticationRepositoryInterface,
) : ViewModel() {

    private val _loginUiState = MutableStateFlow(LoginScreenUiState())
    val uiState: StateFlow<LoginScreenUiState> = _loginUiState.asStateFlow()

    private val _profileUiState = MutableStateFlow(ProfileScreenUiState(isLoggedIn = isLoggedIn()))
    val profileUiState: StateFlow<ProfileScreenUiState> = _profileUiState.asStateFlow()

    private val _isLoggedIn = MutableSharedFlow<Boolean>()
    val isLoggedIn: SharedFlow<Boolean> = _isLoggedIn.asSharedFlow()

    private val _signInUiState = MutableStateFlow(SignInUiState())
    val signInUiState: StateFlow<SignInUiState> = _signInUiState.asStateFlow()


    fun getCurrentUser() = authenticationRepository.getUser()

    fun signIn(user: User) {
        viewModelScope.launch(dispatchers.main) {
            val result = authenticationRepository.signInWithPasswordAndEmail(user, dispatchers.io)

            if (result.status == Status.ERROR) {
                result.message?.let { manageSignInErrorMessage(result, message = it) }
                setSignInHasSignedInSuccessfullyUiState(false)
            } else {
                setSignInHasSignedInSuccessfullyUiState(true)
            }
        }
    }

    fun login(user: User) {
        viewModelScope.launch(dispatchers.main) {
            val result = authenticationRepository.loginWithPasswordAndEmail(user, dispatchers.io)

            if (result.status == Status.ERROR) {
                result.message?.let { manageErrorMessage(result, message = it) }
            } else {
                setIsLoggedIn(true)
                val currentUser = getCurrentUser()

                _profileUiState.update {
                    it.copy(
                        profilePicture = currentUser?.photoUrl.toString(),
                        username = currentUser?.email.toString(),
                    )
                }
            }
        }
    }

    private fun manageErrorMessage(
        result: Resource<AuthResult?>,
        message: String,
    ) {
        when {
            result.message?.contains("email") == true || result.message?.contains("user") == true -> {
                setEmailError(true)
                setEmailSupportingText(message.substringAfter(":"))
            }

            result.message?.contains("password") == true -> {
                setPasswordError(true)
                setPasswordSupportingText(message.substringAfter(":"))
            }

            else -> {
                setSnackbarHostState(true)
                setSnackbarHostMessage(
                    result.message?.substringAfter(":")?.substringBefore(".")
                        ?: "Unknown error"
                )
            }
        }
    }

    private fun manageSignInErrorMessage(
        result: Resource<AuthResult?>,
        message: String,
    ) {
        when {
            result.message?.contains("email") == true || result.message?.contains("user") == true -> {
                setSignInEmailErrorUiState(true)
                setSignInEmailSupportingTextUiState(message.substringAfter(":"))
            }

            result.message?.contains("password") == true -> {
                setSignInPasswordErrorUiState(true)
                setSignInPasswordSupportingTextUiState(message.substringAfter(":"))
            }

            else -> {
                setSnackbarHostState(true)
                setSnackbarHostMessage(
                    result.message?.substringAfter(":")?.substringBefore(".")
                        ?: "Unknown error"
                )
            }
        }
    }

    fun isLoggedIn() = authenticationRepository.isLoggedIn()

    fun loginStatus() {
        viewModelScope.launch(dispatchers.main) {
            _isLoggedIn.emit(authenticationRepository.isLoggedIn())
        }
    }

    fun logout() {
        authenticationRepository.logout()
        _loginUiState.update {
            it.copy(isLoggedIn = isLoggedIn())
        }
        _profileUiState.update {
            it.copy(isLoggedIn = isLoggedIn())
        }
    }


    fun setIsLoggedIn(isLogged: Boolean) {
        _loginUiState.update {
            it.copy(isLoggedIn = isLogged)
        }
        _profileUiState.update {
            it.copy(isLoggedIn = isLogged)
        }
    }

    fun setEmailSupportingText(emailSupportingText: String) {
        _loginUiState.update {
            it.copy(emailSupportingText = emailSupportingText)
        }
    }

    fun setEmailError(isError: Boolean) {
        _loginUiState.update {
            it.copy(emailError = isError)
        }
    }

    fun setEmail(email: String) {
        _loginUiState.update {
            it.copy(email = email)
        }
    }

    fun setPassword(password: String) {
        _loginUiState.update {
            it.copy(password = password)
        }
    }

    fun setPasswordSupportingText(passwordSupportingText: String) {
        _loginUiState.update {
            it.copy(passwordSupportingText = passwordSupportingText)
        }
    }

    fun setPasswordError(isError: Boolean) {
        _loginUiState.update {
            it.copy(passwordError = isError)
        }
    }

    fun setSnackbarHostState(showSnackbar: Boolean) {
        _loginUiState.update {
            it.copy(snackbarHostState = showSnackbar)
        }
    }

    fun setSnackbarHostMessage(message: String) {
        _loginUiState.update {
            it.copy(snackbarMessage = message)
        }
    }

    fun setSignInEmailUiState(email: String) {
        _signInUiState.update {
            it.copy(email = email)
        }
    }

    fun setSignInEmailSupportingTextUiState(emailSupportingText: String) {
        _signInUiState.update {
            it.copy(emailSupportingText = emailSupportingText)
        }
    }

    fun setSignInEmailErrorUiState(isError: Boolean) {
        _signInUiState.update {
            it.copy(emailError = isError)
        }
    }

    fun setSignInPasswordUiState(password: String) {
        _signInUiState.update {
            it.copy(password = password)
        }
    }

    fun setSignInPhoneUiState(phone: String) {
        _signInUiState.update {
            it.copy(phone = phone)
        }
    }

    fun setSignInPhoneSupportingTextUiState(phoneSupportingText: String) {
        _signInUiState.update {
            it.copy(phoneSupportingText = phoneSupportingText)
        }
    }

    fun setSingInConfirmPasswordUiState(confirmPassword: String) {
        _signInUiState.update {
            it.copy(confirmPassword = confirmPassword)
        }
    }

    fun setSignInConfirmPasswordSupportingTextUiState(
        confirmPasswordSupportingText: String,
    ) {
        _signInUiState.update {
            it.copy(confirmPasswordSupportingText = confirmPasswordSupportingText)
        }
    }

    fun setSignInPasswordSupportingTextUiState(passwordSupportingText: String) {
        _signInUiState.update {
            it.copy(passwordSupportingText = passwordSupportingText)
        }
    }

    fun setSingInPasswordErrorUiState(isError: Boolean) {
        _signInUiState.update {
            it.copy(passwordError = isError)
        }
    }

    fun setSignInConfirmPasswordErrorUiState(isError: Boolean) {
        _signInUiState.update {
            it.copy(confirmPasswordError = isError)
        }
    }

    fun setSignInPhoneErrorUiState(isError: Boolean) {
        _signInUiState.update {
            it.copy(phoneError = isError)
        }
    }

    fun setSignInPasswordErrorUiState(isError: Boolean) {
        _signInUiState.update {
            it.copy(passwordError = isError)
        }
    }

    fun setSignInHasSignedInSuccessfullyUiState(value: Boolean) {
        _signInUiState.update {
            it.copy(hasSignedInSuccessfully = value)
        }
    }

}
