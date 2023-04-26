package com.cailloutr.rightnewscompose.data.remote

object HttpRoutes {

    private const val BASE_URL = "https://content.guardianapis.com"
    const val GET_SECTIONS = "$BASE_URL/sections?"
    const val GET_SEARCH = "$BASE_URL/search?"
}

object HttpParams {
    const val API_KEY = "api-key"
    const val SECTION = "section"
    const val SHOW_FIELDS = "show-fields"
    const val ORDER_BY = "order-by"
    const val PAGE = "page"
    const val QUERY = "q"
}