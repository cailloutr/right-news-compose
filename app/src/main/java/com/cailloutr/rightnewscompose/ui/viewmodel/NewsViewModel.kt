package com.cailloutr.rightnewscompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cailloutr.rightnewscompose.constants.Constants.DEFAULT_SECTIONS
import com.cailloutr.rightnewscompose.constants.Constants.LATEST_NEWS
import com.cailloutr.rightnewscompose.data.local.roommodel.toSection
import com.cailloutr.rightnewscompose.data.remote.responses.news.NewsRoot
import com.cailloutr.rightnewscompose.data.remote.responses.sections.SectionsRoot
import com.cailloutr.rightnewscompose.model.SectionWrapper
import com.cailloutr.rightnewscompose.other.DispatchersProvider
import com.cailloutr.rightnewscompose.other.Resource
import com.cailloutr.rightnewscompose.ui.uistate.MainScreenUiState
import com.cailloutr.rightnewscompose.usecases.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val dispatchers: DispatchersProvider,
    private val newsUseCases: NewsUseCases,
) : ViewModel() {


    private val _uiState =
        MutableStateFlow<MainScreenUiState>(MainScreenUiState())
    val uiState: StateFlow<MainScreenUiState>
        get() = _uiState


    init {
        refreshData()
    }

    fun refreshData(responseStatus: (Resource<*>) -> Unit = {}) {
        _uiState.update {
            it.copy(
                isRefreshingAll = true
            )
        }
        viewModelScope.launch(dispatchers.main) {
            getLatestNews(SectionWrapper(sectionName = LATEST_NEWS, value = "")) {
                responseStatus(it)
            }
            getSectionsFilteredById(DEFAULT_SECTIONS) {}
            getNewsBySection {}
            joinAll()
            _uiState.update {
                it.copy(
                    isRefreshingAll = false
                )
            }
        }
    }

    fun setSelectedSection(id: String) {
        _uiState.update {
            it.copy(
                selectedSection = id
            )
        }
    }

    fun getLatestNews(section: SectionWrapper, responseStatus: (Resource<NewsRoot?>) -> Unit) {
        viewModelScope.launch(dispatchers.main) {
            _uiState.update {
                it.copy(
                    latestNews = newsUseCases.getNewsBySectionUseCase(
                        dispatchers.io,
                        section,
                        responseStatus
                    ).first()
                )
            }
        }
    }

    fun getNewsBySection(responseStatus: (Resource<NewsRoot?>) -> Unit) {
        viewModelScope.launch(dispatchers.main) {
            val selectedSection = SectionWrapper(
                sectionName = _uiState.value.selectedSection,
                value = _uiState.value.selectedSection
            )
            if (selectedSection.sectionName.isNotEmpty()) {
                _uiState.update {
                    it.copy(isRefreshingSectionArticles = true)
                }
                _uiState.update {
                    it.copy(
                        sectionArticles = newsUseCases.getNewsBySectionUseCase(
                            dispatchers.io,
                            selectedSection,
                            responseStatus
                        ).first(),
                        isRefreshingSectionArticles = false
                    )
                }
            }
        }
    }

    fun getSectionsFilteredById(
        sections: List<String>? = null,
        responseStatus: (Resource<SectionsRoot?>) -> Unit,
    ) {
        viewModelScope.launch(dispatchers.main) {
            val sectionsList = if (sections != null) {
                newsUseCases.getSectionsUseCase(dispatchers.io, responseStatus).first().filter {
                    sections.contains(it.id)
                }
            } else {
                newsUseCases.getSectionsUseCase(dispatchers.io, responseStatus).first()
            }
            _uiState.update {
                it.copy(
                    sections = sectionsList.map { it.toSection() }
                )
            }
        }
    }
}