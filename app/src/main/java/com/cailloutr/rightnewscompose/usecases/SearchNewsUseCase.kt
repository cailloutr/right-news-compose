package com.cailloutr.rightnewscompose.usecases

import com.cailloutr.rightnewscompose.constants.Constants.SEARCH_NEWS
import com.cailloutr.rightnewscompose.data.remote.responses.news.NewsRoot
import com.cailloutr.rightnewscompose.model.NewsContainer
import com.cailloutr.rightnewscompose.model.SectionWrapper
import com.cailloutr.rightnewscompose.other.Resource
import com.cailloutr.rightnewscompose.repository.NewsRepositoryInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//TODO: Tests
class SearchNewsUseCase @Inject constructor(
    private val repository: NewsRepositoryInterface,
) {

    suspend operator fun invoke(
        searchQuery: String,
        context: CoroutineDispatcher,
        responseStatus: (Resource<NewsRoot?>) -> Unit,
    ): Flow<NewsContainer> {
        repository.searchNews(
            context = context,
            searchQuery = searchQuery,
            responseStatus = responseStatus
        )

        return repository.getNewsOfSection(context, SectionWrapper(SEARCH_NEWS, SEARCH_NEWS))
    }
}