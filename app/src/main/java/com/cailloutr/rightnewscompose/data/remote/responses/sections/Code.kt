package com.cailloutr.rightnewscompose.data.remote.responses.sections

import kotlinx.serialization.Serializable

@Serializable
enum class Code(val value: String) {
    Au("au"),
    Default("default"),
    Uk("uk"),
    Us("us");
}