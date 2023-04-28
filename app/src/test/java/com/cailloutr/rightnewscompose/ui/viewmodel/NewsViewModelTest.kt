package com.cailloutr.rightnewscompose.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.cailloutr.rightnewscompose.TestCoroutineDispatcher
import com.cailloutr.rightnewscompose.TestsConstants
import com.cailloutr.rightnewscompose.data.local.roommodel.toSection
import com.cailloutr.rightnewscompose.data.remote.responses.news.toNewsContainer
import com.cailloutr.rightnewscompose.data.remote.responses.sections.toRoomSections
import com.cailloutr.rightnewscompose.model.SectionWrapper
import com.cailloutr.rightnewscompose.usecases.NewsUseCases
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModelTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: NewsViewModel
    lateinit var testDispatcher: TestCoroutineDispatcher

    @MockK
    lateinit var newsUseCases: NewsUseCases

    @Before
    fun setUp() {
        testDispatcher = TestCoroutineDispatcher()
        viewModel = NewsViewModel(
            testDispatcher,
            newsUseCases
        )
    }

    @Test
    fun test_fetchDataFromApiShouldCallTheCorrectMethods() = runTest {
        // Given
        coEvery {
            newsUseCases.getSectionsUseCase(
                any(),
                any()
            )
        } returns flowOf(TestsConstants.fakeResponseSectionRoot.response.results.map { it.toRoomSections() })

        coEvery { newsUseCases.getNewsBySectionUseCase(any(), any(), any()) } returns flowOf(
            TestsConstants.fakeArticle.response.toNewsContainer("latest-news")
        )

        // When
        viewModel.refreshData()

        // Then
        coVerify { newsUseCases.getSectionsUseCase(testDispatcher.io, any()) }
        coVerify { newsUseCases.getNewsBySectionUseCase(testDispatcher.io, any(), any()) }
        coVerify { newsUseCases.getNewsBySectionUseCase(testDispatcher.io, any(), any()) }
    }

    @Test
    fun test_setSelectedSectionShouldOnlyUpdateUiStateSelectedSection() = runTest {
        val selectedSection = "test"

        viewModel.setSelectedSection(selectedSection)

        assertThat(viewModel.uiState.value.selectedSection).isEqualTo(selectedSection)

    }

    @Test
    fun test_getLatestNewsShouldOnlyUpdateUiStateSelectedSection() {
        val selectedSection = ""
        val newsResponse = TestsConstants.fakeArticle

        coEvery { newsUseCases.getNewsBySectionUseCase(testDispatcher.io, any(), any()) }.returns(
            flowOf(newsResponse.response.toNewsContainer(selectedSection))
        )

        viewModel.getLatestNews(SectionWrapper("latest_news", ""), {})

        assertThat(viewModel.uiState.value.latestNews).isEqualTo(
            newsResponse.response.toNewsContainer(selectedSection)
        )
    }

    @Test
    fun `getNewsBySection should update uiState sectionArticles`() = runTest {
        // Given
        val section = "latest-news"
        val article = TestsConstants.fakeArticle
        val fakeNewsRoot = article.response.toNewsContainer(section)

        every { newsUseCases.getNewsBySectionUseCase(any(), any(), any()) } returns flowOf(
            fakeNewsRoot
        )


        viewModel.setSelectedSection(section)
        viewModel.getNewsBySection { }

        // Then
        viewModel.uiState.test {
            // Check that uiState sectionArticles is updated with fakeNewsRoot.toNewsContainer(section)
            assertThat(awaitItem().sectionArticles).isEqualTo(fakeNewsRoot)
        }
    }

    @Test
    fun `getSectionsFilteredById with null sections should update UI state with all sections`() =
        runTest {
            val sectionsResponse = TestsConstants.fakeResponseSectionRoot

            coEvery { newsUseCases.getSectionsUseCase(any(), any()) }.returns(
                flowOf(
                    sectionsResponse.response.results.map { it.toRoomSections() }
                )
            )

            viewModel.getSectionsFilteredById(null) {}

            viewModel.uiState.test {
                assertThat(awaitItem().sections).isEqualTo(sectionsResponse.response.results.map { it.toRoomSections().toSection() })
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `getSectionsFilteredById with valid sections should update UI state with filtered sections`() =
        runTest {
            val sectionsResponse = TestsConstants.fakeResponseSectionRoot
            val selectedSections = listOf("tech", "books")

            coEvery { newsUseCases.getSectionsUseCase(any(), any()) }.returns(
                flowOf(
                    sectionsResponse.response.results.map { it.toRoomSections() }
                )
            )

            viewModel.getSectionsFilteredById(selectedSections) {}

            viewModel.uiState.test {
                val filteredSections =
                    sectionsResponse.response.results.filter { selectedSections.contains(it.id) }
                assertThat(awaitItem().sections).isEqualTo(filteredSections.map { it.toRoomSections().toSection() })
                cancelAndIgnoreRemainingEvents()
            }
        }

}