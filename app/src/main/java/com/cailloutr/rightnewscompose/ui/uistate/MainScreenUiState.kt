package com.cailloutr.rightnewscompose.ui.uistate

import com.cailloutr.rightnewscompose.constants.Constants
import com.cailloutr.rightnewscompose.model.NewsContainer
import com.cailloutr.rightnewscompose.model.Section

data class MainScreenUiState(
    var sections: List<Section> = listOf(),
    var latestNews: NewsContainer? = null,
    var sectionArticles: NewsContainer? = null,
    var isRefreshingSectionArticles: Boolean = false,
    var isRefreshingAll: Boolean = false,
    var selectedSection: String = Constants.FIRST_SECTIONS_ID,
)