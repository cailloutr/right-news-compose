package com.cailloutr.rightnewscompose.repository

import com.cailloutr.rightnewscompose.data.local.roommodel.RoomSection
import com.cailloutr.rightnewscompose.model.NewsContainer
import com.cailloutr.rightnewscompose.model.SectionWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface NewsRepositoryInterface {

    fun getAllSections(context: CoroutineDispatcher): Flow<List<RoomSection>>

    fun getNewsOfSection(
        context: CoroutineDispatcher,
        section: SectionWrapper,
    ): Flow<NewsContainer>

//    suspend fun cleanCache(context: CoroutineDispatcher, section: String)
//    suspend fun refreshSections(context: CoroutineDispatcher)

    /*suspend fun refreshArticles(
        context: CoroutineDispatcher,
        section: SectionWrapper,
        responseStatus: (message: Resource<Exception>) -> Unit,
    )*/

//    suspend fun fetchSectionsFromApi(context: CoroutineDispatcher)

    /*suspend fun fetchNewsFromApi(
        context: CoroutineDispatcher,
        section: SectionWrapper,
        responseStatus: (message: Resource<Exception>) -> Unit,
    )*/
}