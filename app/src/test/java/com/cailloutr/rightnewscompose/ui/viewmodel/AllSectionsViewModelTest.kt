package com.cailloutr.rightnewscompose.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cailloutr.rightnewscompose.TestCoroutineDispatcher
import com.cailloutr.rightnewscompose.TestsConstants
import com.cailloutr.rightnewscompose.data.local.roommodel.toSection
import com.cailloutr.rightnewscompose.data.remote.responses.sections.toRoomSections
import com.cailloutr.rightnewscompose.extensions.toSectionsByIndex
import com.cailloutr.rightnewscompose.usecases.NewsUseCases
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AllSectionsViewModelTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: AllSectionsViewModel
    lateinit var testDispatcher: TestCoroutineDispatcher

    @MockK
    lateinit var newsUseCases: NewsUseCases

    @Before
    fun setUp() {
        testDispatcher = TestCoroutineDispatcher()
        viewModel = AllSectionsViewModel(
            testDispatcher,
            newsUseCases
        )
    }

    @Test
    fun test_getAllSectionsShouldUpdateUiState() = runTest {
        val response =
            TestsConstants.fakeResponseSectionRoot.response.results.map { it.toRoomSections() }

        coEvery { newsUseCases.getSectionsUseCase(any(), any()) } returns flowOf(response)

        viewModel.getAllSections()

        assertThat(viewModel.uiState.value.sectionsByIndex).isEqualTo(response.map { it.toSection() }
            .toSectionsByIndex())
    }
}