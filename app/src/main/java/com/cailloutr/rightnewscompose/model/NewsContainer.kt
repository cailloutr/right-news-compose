package com.cailloutr.rightnewscompose.model

data class NewsContainer(
    val id: String,
    val total: Long,
    val startIndex: Long,
    val pageSize: Long,
    val currentPage: Long,
    val pages: Long,
    val orderBy: String,
    val results: List<Article>
)