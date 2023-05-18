package com.cailloutr.rightnewscompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cailloutr.rightnewscompose.data.remote.responses.news.NewsRoot
import com.cailloutr.rightnewscompose.other.DispatchersProvider
import com.cailloutr.rightnewscompose.other.Resource
import com.cailloutr.rightnewscompose.other.Status
import com.cailloutr.rightnewscompose.ui.uistate.SearchNewsUiState
import com.cailloutr.rightnewscompose.usecases.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


//TODO: Tests
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val dispatchers: DispatchersProvider,
    private val newsUseCases: NewsUseCases,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchNewsUiState())
    val uiState: StateFlow<SearchNewsUiState> = _uiState.asStateFlow()

    private val _snackbarState = MutableSharedFlow<Status>()
    val snackbarState: SharedFlow<Status> = _snackbarState.asSharedFlow()

    fun getSearchResult(
        searchQuery: String,
        responseStatus: (Resource<NewsRoot?>) -> Unit
    ) {
        _uiState.update {
            it.copy(isRefreshingAll = true)
        }
        viewModelScope.launch(dispatchers.main) {
            _uiState.update {
                it.copy(
                    latestNews = newsUseCases.getSearchNewsUseCase(
                        searchQuery,
                        dispatchers.io,
                        responseStatus
                    ).first(),
                    isRefreshingAll = false,
                )
            }
        }
    }

    fun setUiStateStatus(status: Status) {
        _uiState.update {
            it.copy(status = status)
        }
    }

    fun setSnackbarState(status: Status) {
        viewModelScope.launch(dispatchers.main) {
            _snackbarState.emit(status)
        }
    }
}