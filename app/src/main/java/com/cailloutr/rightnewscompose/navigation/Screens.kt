package com.cailloutr.rightnewscompose.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.cailloutr.rightnewscompose.R
import com.cailloutr.rightnewscompose.extensions.toRouteId

sealed class BottomBarScreens(
    val route: String,
    @StringRes val stringRes: Int,
    @DrawableRes val drawableRes: Int? = null,
) {
    object Main :
        BottomBarScreens("main_screen", R.string.main_screen, R.drawable.main_screen)

    object Favorite :
        BottomBarScreens("favorite_screen", R.string.favorite_screen, R.drawable.favorite_screen)

    object Profile :
        BottomBarScreens("profile_screen", R.string.profile_screen, R.drawable.profile_screen)

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/${arg.toRouteId()}")
            }
        }
    }
}

sealed class DetailsScreen(val route: String) {

    object Details :
        DetailsScreen("details_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/${arg.toRouteId()}")
            }
        }
    }
}

sealed class AllSectionsScreen(val route: String) {

    object AllSections :
        AllSectionsScreen("all_sections_screen")
}

sealed class LatestNewsScreen(val route: String) {
    object LatestNews : LatestNewsScreen("latest_news_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/${arg.toRouteId()}")
            }
        }
    }
}

sealed class SearchNewsScreen(val route: String) {
    object Search : SearchNewsScreen("search_news_screen")
}

object Args {
    const val ARTICLE_ID = "articleId"
    const val SECTION_ID = "sectionId"
    const val SECTION_TITLE = "sectionTitle"
}
