package com.cailloutr.rightnewscompose.data.remote.responses.sections

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SectionsEdition(
    val id: String,
    val webTitle: String,

    @SerialName("webUrl")
    val webURL: String,

    @SerialName("apiUrl")
    val apiURL: String,

    val code: String
)

