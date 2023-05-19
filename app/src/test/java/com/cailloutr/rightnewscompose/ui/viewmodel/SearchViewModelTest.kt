package com.cailloutr.rightnewscompose.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.cailloutr.rightnewscompose.TestCoroutineDispatcher
import com.cailloutr.rightnewscompose.model.NewsContainer
import com.cailloutr.rightnewscompose.other.Status.ERROR
import com.cailloutr.rightnewscompose.other.Status.SUCCESS
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
class SearchViewModelTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: SearchViewModel
    lateinit var testDispatcher: TestCoroutineDispatcher

    @MockK
    lateinit var newsUseCases: NewsUseCases

    @Before
    fun setUp() {
        testDispatcher = TestCoroutineDispatcher()
        viewModel = SearchViewModel(
            testDispatcher,
            newsUseCases
        )
    }

    @Test
    fun test_getSearchResultShouldUpdateUiState() = runTest {

        val response = NewsContainer(
            id = "id",
            total = 10L,
            startIndex = 0L,
            pageSize = 10L,
            currentPage = 0L,
            pages = 10L,
            orderBy = "newest",
            results = emptyList()
        )

        coEvery {
            newsUseCases.getSearchNewsUseCase(
                any(),
                any(),
                any()
            )
        } returns flowOf(response)

        viewModel.getSearchResult("search_query", {})

        viewModel.uiState.test {
            val item = awaitItem()

            assertThat(item.latestNews).isEqualTo(response)
            assertThat(item.isRefreshingAll).isFalse()
        }
    }

    @Test
    fun test_setUiStateStatusShouldUpdateUiStateStatus() = runTest {
        val status = SUCCESS

        viewModel.setUiStateStatus(status)

        viewModel.uiState.test {
            assertThat(awaitItem().status).isEqualTo(status)
        }
    }

    @Test
    fun setSnackbarState() = runTest {
        val status = ERROR
        viewModel.snackbarState.test {
            viewModel.setSnackbarState(status)

            assertThat(awaitItem()).isEqualTo(status)
        }
    }
}