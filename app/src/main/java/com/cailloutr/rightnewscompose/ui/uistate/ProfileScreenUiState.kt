package com.cailloutr.rightnewscompose.ui.uistate

data class ProfileScreenUiState(
    val profilePicture: String = "",
    val username: String = "",
    val userLocation: String = "",
    val isLoggedIn: Boolean
)
