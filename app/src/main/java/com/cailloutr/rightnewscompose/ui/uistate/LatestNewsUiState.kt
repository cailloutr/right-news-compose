package com.cailloutr.rightnewscompose.ui.uistate

import com.cailloutr.rightnewscompose.model.NewsContainer

data class LatestNewsUiState(
    var latestNews: NewsContainer? = null,
    var isRefreshingAll: Boolean = false,
    var title: String = ""
)
