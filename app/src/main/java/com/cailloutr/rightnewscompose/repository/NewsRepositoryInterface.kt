package com.cailloutr.rightnewscompose.repository

import com.cailloutr.rightnewscompose.data.local.roommodel.RoomArticle
import com.cailloutr.rightnewscompose.data.local.roommodel.RoomSection
import com.cailloutr.rightnewscompose.data.remote.responses.news.NewsRoot
import com.cailloutr.rightnewscompose.data.remote.responses.sections.SectionsRoot
import com.cailloutr.rightnewscompose.model.NewsContainer
import com.cailloutr.rightnewscompose.model.SectionWrapper
import com.cailloutr.rightnewscompose.other.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface NewsRepositoryInterface {

    fun getArticleById(articleId: String): Flow<RoomArticle?>


    fun getAllSections(): Flow<List<RoomSection>>

    fun getNewsOfSection(
        context: CoroutineDispatcher,
        section: SectionWrapper,
    ): Flow<NewsContainer>

    suspend fun fetchSectionsFromApi(
        context: CoroutineDispatcher,
        responseStatus: (Resource<SectionsRoot?>) -> Unit,
    )

    suspend fun refreshSections(
        context: CoroutineDispatcher,
        responseStatus: (Resource<SectionsRoot?>) -> Unit,
    )

    suspend fun cleanCache(context: CoroutineDispatcher, section: String)

    suspend fun refreshArticles(
        context: CoroutineDispatcher,
        section: SectionWrapper,
        responseStatus: (Resource<NewsRoot?>) -> Unit,
    )

    suspend fun fetchArticlesFromApi(
        context: CoroutineDispatcher,
        section: SectionWrapper,
        responseStatus: (Resource<NewsRoot?>) -> Unit,
    )
}