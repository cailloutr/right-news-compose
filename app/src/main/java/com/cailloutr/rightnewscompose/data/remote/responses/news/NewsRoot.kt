package com.cailloutr.rightnewscompose.data.remote.responses.news

import kotlinx.serialization.Serializable

@Serializable
data class NewsRoot(
    val response: NewsResponse
)