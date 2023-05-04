package com.cailloutr.rightnewscompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cailloutr.rightnewscompose.data.local.roommodel.toArticle
import com.cailloutr.rightnewscompose.other.DispatchersProvider
import com.cailloutr.rightnewscompose.ui.uistate.DetailsScreenUiState
import com.cailloutr.rightnewscompose.usecases.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val dispatchers: DispatchersProvider,
    private val newsUseCases: NewsUseCases,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsScreenUiState())
    val uiState: StateFlow<DetailsScreenUiState> = _uiState.asStateFlow()

    fun getArticleById(id: String) {
        viewModelScope.launch(dispatchers.main) {
            val article = newsUseCases.getArticleById(id).first()?.toArticle()

            article?.let {
                _uiState.update {
                    it.copy(
                        thumbnail = article.thumbnail ?: "",
                        publicationDate = article.webPublicationDate,
                        title = article.webTitle,
                        body = article.body ?: "",
                        webUrl = article.webUrl
                    )
                }
            }
        }
    }
}