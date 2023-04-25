package com.cailloutr.rightnewscompose.repository

import com.cailloutr.rightnewscompose.data.local.NewsDatabase
import com.cailloutr.rightnewscompose.data.local.roommodel.RoomSection
import com.cailloutr.rightnewscompose.data.local.roommodel.toArticle
import com.cailloutr.rightnewscompose.model.NewsContainer
import com.cailloutr.rightnewscompose.model.SectionWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


class NewsRepository @Inject constructor(
    private val database: NewsDatabase,
) : NewsRepositoryInterface {

    override fun getAllSections(context: CoroutineDispatcher): Flow<List<RoomSection>> {
        return database.sectionDao.getAllSection()
    }

    override fun getNewsOfSection(
        context: CoroutineDispatcher,
        section: SectionWrapper,
    ): Flow<NewsContainer> = channelFlow {
        database.newsContainerDao.getNewsContainer(section.sectionName)
            .collectLatest { roomNewsContainer ->
                roomNewsContainer?.let {
                    database.articleDao.getAllArticlesFromSection(roomNewsContainer.id)
                        .collectLatest { roomArticleList ->
                            roomArticleList?.let {
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
    }

}