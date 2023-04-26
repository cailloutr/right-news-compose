package com.cailloutr.rightnewscompose.repository

import com.cailloutr.rightnewscompose.data.local.NewsDatabase
import com.cailloutr.rightnewscompose.data.local.roommodel.RoomArticle
import com.cailloutr.rightnewscompose.data.local.roommodel.RoomSection
import com.cailloutr.rightnewscompose.data.local.roommodel.toArticle
import com.cailloutr.rightnewscompose.data.remote.TheGuardianServiceImpl
import com.cailloutr.rightnewscompose.data.remote.responses.news.NewsRoot
import com.cailloutr.rightnewscompose.data.remote.responses.news.toRoomArticle
import com.cailloutr.rightnewscompose.data.remote.responses.news.toRoomNewsContainer
import com.cailloutr.rightnewscompose.data.remote.responses.sections.SectionsRoot
import com.cailloutr.rightnewscompose.data.remote.responses.sections.toRoomSections
import com.cailloutr.rightnewscompose.model.NewsContainer
import com.cailloutr.rightnewscompose.model.SectionWrapper
import com.cailloutr.rightnewscompose.other.Resource
import com.cailloutr.rightnewscompose.other.Status
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject


class NewsRepository @Inject constructor(
    private val database: NewsDatabase,
    private val serviceImpl: TheGuardianServiceImpl,
) : NewsRepositoryInterface {

    override fun getAllSections(): Flow<List<RoomSection>> {
        return database.sectionDao.getAllSection()
    }

    override fun getNewsOfSection(
        context: CoroutineDispatcher,
        section: SectionWrapper,
    ): Flow<NewsContainer> = channelFlow {
        database.newsContainerDao.getNewsContainer(section.sectionName)
            .collectLatest { roomNewsContainer ->
                roomNewsContainer?.let {
                    database.articleDao.getAllArticlesFromSection(section.sectionName)
                        .collectLatest { roomArticleList ->
                            send(
                                NewsContainer(
                                    id = roomNewsContainer.id,
                                    total = roomNewsContainer.total,
                                    startIndex = roomNewsContainer.startIndex,
                                    pageSize = roomNewsContainer.pageSize,
                                    currentPage = roomNewsContainer.currentPage,
                                    pages = roomNewsContainer.pages,
                                    orderBy = roomNewsContainer.orderBy,
                                    results = roomArticleList.map { it.toArticle() }
                                )
                            )
                        }
                }
            }
    }

    override suspend fun fetchSectionsFromApi(
        context: CoroutineDispatcher,
        responseStatus: (Resource<SectionsRoot?>) -> Unit,
    ) {
        val response: Resource<SectionsRoot?> = serviceImpl.getAllSections()

        if (response.status == Status.SUCCESS) {
            val body: List<RoomSection>? = response.data?.response?.results?.map { result ->
                result.toRoomSections()
            }

            body?.let { listRoomSections ->
                withContext(context) {
                    database.sectionDao.insertSection(*listRoomSections.toTypedArray())
                }
            }
        } else {
            responseStatus(response)
        }
    }

    override suspend fun refreshSections(
        context: CoroutineDispatcher,
        responseStatus: (Resource<SectionsRoot?>) -> Unit,
    ) {
        fetchSectionsFromApi(context, responseStatus)
    }

    override suspend fun cleanCache(context: CoroutineDispatcher, section: String) {
        val cachedData: List<RoomArticle> =
            database.articleDao.getAllArticlesFromSection(section).first()
        database.articleDao.deleteArticle(*cachedData.toTypedArray())
    }

    override suspend fun refreshArticles(
        context: CoroutineDispatcher,
        section: SectionWrapper,
        responseStatus: (Resource<NewsRoot?>) -> Unit,
    ) {
        fetchArticlesFromApi(context, section, responseStatus)
    }

    override suspend fun fetchArticlesFromApi(
        context: CoroutineDispatcher,
        section: SectionWrapper,
        responseStatus: (Resource<NewsRoot?>) -> Unit,
    ) {
        val response: Resource<NewsRoot> = serviceImpl.getNewsOfSection(section.value)

        if (response.status == Status.SUCCESS) {

            cleanCache(context, section.sectionName)

            val container = response.data?.response?.toRoomNewsContainer(section.sectionName)
            container?.let {
                val articles =
                    response.data.response.results.map { it.toRoomArticle(containerId = container.id) }

                withContext(context) {
                    database.newsContainerDao.insert(container)
                    database.articleDao.insertArticle(*articles.toTypedArray())
                }
            }
        }
        responseStatus(response)
    }
}