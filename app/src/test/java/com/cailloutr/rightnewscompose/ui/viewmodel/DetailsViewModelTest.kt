package com.cailloutr.rightnewscompose.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cailloutr.rightnewscompose.TestCoroutineDispatcher
import com.cailloutr.rightnewscompose.TestsConstants
import com.cailloutr.rightnewscompose.data.remote.responses.news.toRoomArticle
import com.cailloutr.rightnewscompose.ui.uistate.DetailsScreenUiState
import com.cailloutr.rightnewscompose.usecases.NewsUseCases
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailsViewModelTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


    lateinit var viewModel: DetailsViewModel
    lateinit var testDispatcher: TestCoroutineDispatcher

    @MockK
    lateinit var newsUseCases: NewsUseCases

    @Before
    fun setUp() {
        testDispatcher = TestCoroutineDispatcher()
        viewModel = DetailsViewModel(
            testDispatcher,
            newsUseCases
        )
    }

    @Test
    fun test_getArticleByIdShouldUpdateViewModelUiState() {
        val articleId = "games/2023/mar/28/super-mario-lush-soaps1"

        val article =
            TestsConstants.fakeArticle.response.results[0].toRoomArticle("latest-news")

        coEvery { newsUseCases.getArticleById(articleId) } returns flowOf(article)

        viewModel.getArticleById(articleId)

        assertThat(viewModel.uiState.value.title).isEqualTo(article.webTitle)
        assertThat(viewModel.uiState.value.body).isEqualTo(article.body)
        assertThat(viewModel.uiState.value.thumbnail).isEqualTo(article.thumbnail)
        assertThat(viewModel.uiState.value.publicationDate).isEqualTo(article.webPublicationDate)
    }

    @Test
    fun test_getArticleByIdWhenArticleIsNullShouldNotUpdateViewModelUiState() {
        val articleId = "games/2023/mar/28/super-mario-lush-soaps1"

        val article = null

        coEvery { newsUseCases.getArticleById(articleId) } returns flowOf(article)

        viewModel.getArticleById(articleId)

        assertThat(viewModel.uiState.value).isEqualTo(DetailsScreenUiState())
    }
}