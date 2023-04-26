package com.cailloutr.rightnewscompose.usecases

import com.cailloutr.rightnewscompose.data.local.roommodel.RoomSection
import com.cailloutr.rightnewscompose.data.remote.responses.sections.SectionsRoot
import com.cailloutr.rightnewscompose.other.Resource
import com.cailloutr.rightnewscompose.repository.NewsRepositoryInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetSectionsUseCase @Inject constructor(
    private val repository: NewsRepositoryInterface
) {

    operator fun invoke(
        context: CoroutineDispatcher,
        responseStatus: (Resource<SectionsRoot?>) -> Unit
    ): Flow<List<RoomSection>> {
        CoroutineScope(context).launch {
            repository.refreshSections(context, responseStatus)
        }
        return repository.getAllSections()
    }
}