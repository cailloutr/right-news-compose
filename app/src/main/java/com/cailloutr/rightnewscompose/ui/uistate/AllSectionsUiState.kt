package com.cailloutr.rightnewscompose.ui.uistate

import com.cailloutr.rightnewscompose.model.SectionsByIndex

data class AllSectionsUiState(
    val sectionsByIndex: List<SectionsByIndex> = emptyList()
)