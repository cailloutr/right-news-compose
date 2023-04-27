package com.cailloutr.rightnewscompose.model

data class Section(
    val id: String,
    val title: String,
    val webUrl: String,
    val apiUrl: String,
    val code: String
)

fun Section.toChipItem() =
    ChipItem(
        id = id,
        text = title
    )