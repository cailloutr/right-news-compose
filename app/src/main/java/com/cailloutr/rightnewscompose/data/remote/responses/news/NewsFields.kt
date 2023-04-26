package com.cailloutr.rightnewscompose.data.remote.responses.news

import kotlinx.serialization.Serializable

@Serializable
data class NewsFields(
    val headline: String,
    val trailText: String,
    val thumbnail: String,
    val body: String,
)