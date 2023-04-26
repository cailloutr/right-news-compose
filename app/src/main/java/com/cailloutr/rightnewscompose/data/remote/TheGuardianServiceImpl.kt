package com.cailloutr.rightnewscompose.data.remote

import com.cailloutr.rightnewscompose.enums.OrderBy
import com.cailloutr.rightnewscompose.BuildConfig
import com.cailloutr.rightnewscompose.constants.Constants
import com.cailloutr.rightnewscompose.data.remote.HttpParams.API_KEY
import com.cailloutr.rightnewscompose.data.remote.HttpParams.SECTION
import com.cailloutr.rightnewscompose.data.remote.HttpParams.SHOW_FIELDS
import com.cailloutr.rightnewscompose.data.remote.responses.news.NewsRoot
import com.cailloutr.rightnewscompose.data.remote.responses.sections.SectionsRoot
import com.cailloutr.rightnewscompose.extensions.getResponse
import com.cailloutr.rightnewscompose.other.Resource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import javax.inject.Inject

const val TAG = "TheGuardianServiceImpl"

class TheGuardianServiceImpl @Inject constructor(
    private val client: HttpClient,
) : TheGuardianService {

    override suspend fun getAllSections(): Resource<SectionsRoot?> {
        return client.getResponse {
            client.get() {
                url(HttpRoutes.GET_SECTIONS)
                parameter(key = API_KEY, BuildConfig.API_KEY)
            }
        }
    }

    override suspend fun getNewsOfSection(section: String): Resource<NewsRoot> {
        return client.getResponse {
            it.get {
                url(HttpRoutes.GET_SEARCH)
                parameter(key = API_KEY, BuildConfig.API_KEY)
                if (section.isNotEmpty()) {
                    parameter(key = SECTION, section)
                }
                parameter(key = SHOW_FIELDS, Constants.API_CALL_FIELDS)
            }
        }
    }

    override suspend fun getNewsOrderedByDate(
        orderBy: OrderBy,
        fields: String,
        page: Int,
    ): NewsRoot {
        TODO("Not yet implemented")
    }

    override suspend fun searchNews(
        orderBy: OrderBy,
        fields: String,
        searchQuery: String,
    ): NewsRoot {
        TODO("Not yet implemented")
    }
}