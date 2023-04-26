package com.cailloutr.rightnewscompose.usecases

import com.cailloutr.rightnewscompose.data.remote.responses.news.NewsRoot
import com.cailloutr.rightnewscompose.model.NewsContainer
import com.cailloutr.rightnewscompose.model.SectionWrapper
import com.cailloutr.rightnewscompose.other.Resource
import com.cailloutr.rightnewscompose.repository.NewsRepositoryInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetNewsBySectionUseCase @Inject constructor(
    private val repository: NewsRepositoryInterface,
) {

    operator fun invoke(
        context: CoroutineDispatcher,
        section: SectionWrapper,
        responseStatus: (Resource<NewsRoot?>) -> Unit
    ): Flow<NewsContainer> {
        CoroutineScope(context).launch {
            repository.refreshArticles(context, section, responseStatus)
        }
        return repository.getNewsOfSection(context, section)
    }
}