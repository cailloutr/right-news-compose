package com.cailloutr.rightnewscompose.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.cailloutr.rightnewscompose.R

sealed class HomeScreens(
    val route: String,
    @StringRes val stringRes: Int,
    @DrawableRes val drawableRes: Int? = null,
) {
    object MainScreen :
        HomeScreens("main_screen", R.string.main_screen, R.drawable.main_screen)
    object FavoriteScreen :
        HomeScreens("favorite_screen", R.string.favorite_screen, R.drawable.favorite_screen)

    object ProfileScreen :
        HomeScreens("profile_screen", R.string.profile_screen, R.drawable.profile_screen)

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
