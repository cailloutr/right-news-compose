package com.cailloutr.rightnewscompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cailloutr.rightnewscompose.constants.Constants
import com.cailloutr.rightnewscompose.data.remote.responses.news.NewsRoot
import com.cailloutr.rightnewscompose.model.SectionWrapper
import com.cailloutr.rightnewscompose.other.DispatchersProvider
import com.cailloutr.rightnewscompose.other.Resource
import com.cailloutr.rightnewscompose.ui.uistate.LatestNewsUiState
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
class LatestNewsViewModel @Inject constructor(
    private val dispatchers: DispatchersProvider,
    private val newsUseCases: NewsUseCases,
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<LatestNewsUiState>(LatestNewsUiState(isRefreshingAll = true))
    val uiState: StateFlow<LatestNewsUiState> = _uiState.asStateFlow()


    fun setUiStateTitle(title: String) {
        _uiState.update {
            it.copy(
                title = title
            )
        }
    }

    fun getLatestNewsBySection(
        section: SectionWrapper,
        responseStatus: (Resource<NewsRoot?>) -> Unit,
    ) {
        viewModelScope.launch(dispatchers.main) {
            val latestNews =
                newsUseCases.getNewsBySectionUseCase(dispatchers.io, section, responseStatus)
                    .first()

            _uiState.update {
                if (section.sectionName != Constants.LATEST_NEWS) {
                    it.copy(
                        latestNews = latestNews,
                        isRefreshingAll = false
                    )
                } else {
                    it.copy(
                        latestNews = latestNews,
                        isRefreshingAll = false
                    )
                }
            }
        }
    }


}