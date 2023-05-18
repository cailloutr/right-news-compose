package com.cailloutr.rightnewscompose.ui.uistate

import com.cailloutr.rightnewscompose.model.NewsContainer
import com.cailloutr.rightnewscompose.other.Status

data class SearchNewsUiState(
    var latestNews: NewsContainer? = null,
    var isRefreshingAll: Boolean = false,
    var status: Status? = null
)
