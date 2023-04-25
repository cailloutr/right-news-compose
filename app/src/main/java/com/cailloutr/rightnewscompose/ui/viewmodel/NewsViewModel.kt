package com.cailloutr.rightnewscompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.cailloutr.rightnewscompose.model.Article
import com.cailloutr.rightnewscompose.model.Banner
import com.cailloutr.rightnewscompose.model.ChipItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(

) : ViewModel() {

    private val _bannerListState = MutableStateFlow(
        List(5) {
            Banner(it.toLong(), it.toString(), it.toString(), it.toString())
        }
    )
    val bannerListState: StateFlow<List<Banner>> = _bannerListState.asStateFlow()

    private val _sectionsListState = MutableStateFlow(
        List(7) {
            ChipItem(
                id = it.toString(),
                text = it.toString(),
            )
        }
    )
    val sectionsListState: StateFlow<List<ChipItem>> = _sectionsListState.asStateFlow()

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
    val isRefreshingSectionsNewsState: StateFlow<Boolean> =
        _isRefreshingSectionsNewsState.asStateFlow()

    private val _selectedSection = MutableStateFlow(sectionsListState.value[0].id)
    val selectedSection: StateFlow<String> = _selectedSection.asStateFlow()

    fun setSelectedSection(id: String) {
        _selectedSection.value = id
    }
}