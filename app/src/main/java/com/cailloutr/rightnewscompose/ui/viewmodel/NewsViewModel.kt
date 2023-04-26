package com.cailloutr.rightnewscompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cailloutr.rightnewscompose.constants.Constants.DEFAULT_SECTIONS
import com.cailloutr.rightnewscompose.constants.Constants.LATEST_NEWS
import com.cailloutr.rightnewscompose.data.local.roommodel.RoomSection
import com.cailloutr.rightnewscompose.data.remote.responses.news.NewsRoot
import com.cailloutr.rightnewscompose.data.remote.responses.sections.SectionsRoot
import com.cailloutr.rightnewscompose.model.Article
import com.cailloutr.rightnewscompose.model.NewsContainer
import com.cailloutr.rightnewscompose.model.SectionWrapper
import com.cailloutr.rightnewscompose.other.DispatchersProvider
import com.cailloutr.rightnewscompose.other.Resource
import com.cailloutr.rightnewscompose.usecases.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val dispatchers: DispatchersProvider,
    private val newsUseCases: NewsUseCases,
) : ViewModel() {

    init {
        fetchDataFromApi()
    }

    fun fetchDataFromApi(responseStatus: (Resource<Exception>) -> Unit = {}) {
        getSectionsFilteredById(DEFAULT_SECTIONS) {}
        getLatestNews(SectionWrapper(sectionName = LATEST_NEWS, value = "")) {}
    }

    private val _sectionsListState = MutableStateFlow<List<RoomSection>>(listOf())
    val sectionsListState: StateFlow<List<RoomSection>> = _sectionsListState.asStateFlow()

    private val _selectedSection = MutableStateFlow("")
    val selectedSection: StateFlow<String> = _selectedSection.asStateFlow()

    private val _latestNewsState = MutableStateFlow<NewsContainer?>(null)
    val latestNewsState: StateFlow<NewsContainer?> = _latestNewsState.asStateFlow()

    private val _articlesState = MutableStateFlow(
        List(10) {
            Article(
                it.toString(),
                it.toString(),
                it.toString(),
                it.toString(),
                it.toString(),
                it.toString(),
                it.toString(),
                it.toString(),
                false,
                it.toString(),
                it.toString(),
                it.toString(),
                it.toString(),
                it.toString(),
                it.toString()
            )
        }
    )

    val articlesState: StateFlow<List<Article>> = _articlesState.asStateFlow()

    private val _isRefreshingSectionsNewsState = MutableStateFlow(false)
    val isRefreshingSectionsNewsState: StateFlow<Boolean> = _isRefreshingSectionsNewsState.asStateFlow()

    fun setSelectedSection(id: String) {
        viewModelScope.launch(dispatchers.main) {
            _selectedSection.emit(id)
        }
    }

    fun getLatestNews(section: SectionWrapper, responseStatus: (Resource<NewsRoot?>) -> Unit) {
        viewModelScope.launch(dispatchers.main) {
            _latestNewsState.emit(
                newsUseCases.getNewsBySectionUseCase(dispatchers.io, section, responseStatus).first()
            )
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
            _selectedSection.emit(sectionsList.first().id)
            _sectionsListState.emit(sectionsList)
        }
    }


}