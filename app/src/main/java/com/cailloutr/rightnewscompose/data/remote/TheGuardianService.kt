package com.cailloutr.rightnewscompose.data.remote

import com.cailloutr.rightnewscompose.constants.Constants
import com.cailloutr.rightnewscompose.enums.OrderBy
import com.cailloutr.rightnewscompose.data.remote.responses.news.NewsRoot
import com.cailloutr.rightnewscompose.data.remote.responses.sections.SectionsRoot
import com.cailloutr.rightnewscompose.other.Resource

interface TheGuardianService {

    suspend fun getAllSections(): Resource<SectionsRoot?>

    suspend fun getNewsOfSection(
        section: String
    ): Resource<NewsRoot>


    suspend fun searchNews(
        orderBy: String = OrderBy.NEWEST.value,
        fields: String = Constants.API_CALL_FIELDS,
        searchQuery: String
    ): Resource<NewsRoot>
}