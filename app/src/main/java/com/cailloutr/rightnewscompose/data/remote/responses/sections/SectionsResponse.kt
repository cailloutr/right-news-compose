package com.cailloutr.rightnewscompose.data.remote.responses.sections

import kotlinx.serialization.Serializable

@Serializable
data class SectionsResponse(
    val status: String,
    val userTier: String,
    val total: Long,
    val results: List<SectionsResult>,
)

