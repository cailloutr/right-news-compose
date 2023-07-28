package com.cailloutr.rightnewscompose.ui.uistate

data class LoginScreenUiState(
    val email: String = "",
    val password: String = "",
    val emailSupportingText: String = "",
    val emailError: Boolean = false,
    val passwordSupportingText: String = "",
    val passwordError: Boolean = false,
    var isLoggedIn: Boolean = false,
    val snackbarHostState: Boolean = false,
    val snackbarMessage: String = ""
) {
}