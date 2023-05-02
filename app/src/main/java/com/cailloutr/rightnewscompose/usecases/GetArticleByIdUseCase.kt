package com.cailloutr.rightnewscompose.usecases

import com.cailloutr.rightnewscompose.data.local.roommodel.RoomArticle
import com.cailloutr.rightnewscompose.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticleByIdUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(
        articleId: String
    ): Flow<RoomArticle?> {
        return repository.getArticleById(articleId)
    }
}
