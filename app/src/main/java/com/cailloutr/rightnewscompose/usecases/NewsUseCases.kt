package com.cailloutr.rightnewscompose.usecases

data class NewsUseCases(
    val getSectionsUseCase: GetSectionsUseCase,
    val getNewsBySectionUseCase: GetNewsBySectionUseCase,
    val getArticleById: GetArticleByIdUseCase,
    val getSearchNewsUseCase: SearchNewsUseCase
)
