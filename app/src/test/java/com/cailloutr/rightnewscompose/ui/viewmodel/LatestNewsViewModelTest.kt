package com.cailloutr.rightnewscompose.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cailloutr.rightnewscompose.TestCoroutineDispatcher
import com.cailloutr.rightnewscompose.model.NewsContainer
import com.cailloutr.rightnewscompose.model.SectionWrapper
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
class LatestNewsViewModelTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: LatestNewsViewModel
    lateinit var testDispatcher: TestCoroutineDispatcher

    @MockK
    lateinit var newsUseCases: NewsUseCases

    @Before
    fun setUp() {
        testDispatcher = TestCoroutineDispatcher()
        viewModel = LatestNewsViewModel(
            testDispatcher,
            newsUseCases
        )
    }

    @Test
    fun test_setUiStateTitleShouldUpdateUiStateTitle() {
        val title = "Title"

        viewModel.setUiStateTitle(title)

        assertThat(viewModel.uiState.value.title).isEqualTo(title)
    }

    @Test
    fun test_getLatestNewsBySectionShouldUpdateUiStateLatestNews() = runTest {
        val newsContainer = NewsContainer(
            id = "id",
            total = 10L,
            startIndex = 0L,
            pageSize = 10L,
            currentPage = 0L,
            pages = 10L,
            orderBy = "newest",
            results = emptyList()
        )

        coEvery { newsUseCases.getNewsBySectionUseCase(any(), any(), any()) } returns flowOf(
            newsContainer
        )

        viewModel.getLatestNewsBySection(
            SectionWrapper("id", "id")
        ) {}

        assertThat(viewModel.uiState.value.latestNews).isEqualTo(newsContainer)
    }
}