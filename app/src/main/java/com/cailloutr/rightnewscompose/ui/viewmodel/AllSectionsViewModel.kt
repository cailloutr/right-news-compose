package com.cailloutr.rightnewscompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cailloutr.rightnewscompose.data.local.roommodel.toSection
import com.cailloutr.rightnewscompose.extensions.toSectionsByIndex
import com.cailloutr.rightnewscompose.model.Section
import com.cailloutr.rightnewscompose.other.DispatchersProvider
import com.cailloutr.rightnewscompose.ui.uistate.AllSectionsUiState
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
class AllSectionsViewModel @Inject constructor(
    private val dispatchers: DispatchersProvider,
    private val newsUseCases: NewsUseCases,
) : ViewModel() {

    private val _uiState = MutableStateFlow<AllSectionsUiState>(AllSectionsUiState())
    val uiState: StateFlow<AllSectionsUiState> = _uiState.asStateFlow()

    init {
        getAllSections()
    }

    fun getAllSections() {
        viewModelScope.launch(dispatchers.main) {
            val sections = newsUseCases.getSectionsUseCase(dispatchers.io, {}).first()

            sections.let { section ->
                val list: List<Section> =
                    sections.map { roomSection -> roomSection.toSection() }

                _uiState.update {
                    it.copy(
                        sectionsByIndex = list.toSectionsByIndex()
                    )
                }
            }
        }
    }
}