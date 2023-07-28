package com.cailloutr.rightnewscompose.ui.uistate

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val confirmPasswordSupportingText: String = "",
    val confirmPasswordError: Boolean = false,
    val phone: String = "",
    val phoneSupportingText: String = "",
    val phoneError: Boolean = false,
    val emailSupportingText: String = "",
    val emailError: Boolean = false,
    val passwordSupportingText: String = "",
    val passwordError: Boolean = false,
    var isLoggedIn: Boolean = false,
    val snackbarHostState: Boolean = false,
    val snackbarMessage: String = "",
    val hasSignedInSuccessfully: Boolean = false
)
