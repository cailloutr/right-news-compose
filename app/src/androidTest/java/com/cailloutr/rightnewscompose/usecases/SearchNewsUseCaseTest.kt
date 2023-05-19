package com.cailloutr.rightnewscompose.usecases


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.cailloutr.rightnewscompose.TestCoroutineDispatcher
import com.cailloutr.rightnewscompose.TestsConstants
import com.cailloutr.rightnewscompose.constants.Constants
import com.cailloutr.rightnewscompose.constants.Constants.TEST_DB
import com.cailloutr.rightnewscompose.data.local.NewsDatabase
import com.cailloutr.rightnewscompose.data.remote.TheGuardianServiceImpl
import com.cailloutr.rightnewscompose.data.remote.responses.news.toNewsContainer
import com.cailloutr.rightnewscompose.other.Resource
import com.cailloutr.rightnewscompose.repository.NewsRepository
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@SmallTest
class SearchNewsUseCaseTest {

    @get:Rule
    val mockRule = MockKRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private lateinit var searchNewsUseCase: SearchNewsUseCase
    private lateinit var testDispatcher: TestCoroutineDispatcher

    private lateinit var repository: NewsRepository

    @MockK
    lateinit var service: TheGuardianServiceImpl

    @Inject
    @Named(TEST_DB)
    lateinit var database: NewsDatabase

    @Before
    fun setUp() {
        hiltRule.inject()
        repository = spyk(NewsRepository(database, service))
        searchNewsUseCase = SearchNewsUseCase(repository)
        testDispatcher = TestCoroutineDispatcher()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun test_invokeShouldCallSearchNewsAndReturnArticlesFromTheDatabase() = runTest {
        val response = Resource.success(TestsConstants.fakeArticle)
        val searchQuery = Constants.SEARCH_NEWS

        coEvery { service.searchNews(searchQuery = any()) } returns response

        val result = searchNewsUseCase(searchQuery, testDispatcher.io) {}.first()

        assertThat(result).isEqualTo(response.data?.response?.toNewsContainer(searchQuery))

        coVerify {
            repository.searchNews(any(), any(), any(), any(), any(), any())
        }
    }

    @Test
    fun test_invokeWhitInvalidQueryShouldJustCallSearchNews() = runTest {
        val response = Resource.error(msg = "Error", data = null)
        val searchQuery = "invalid_query"

        coEvery { service.searchNews(searchQuery = any()) } returns response

        searchNewsUseCase(searchQuery, testDispatcher.io) {}

        coVerify {
            repository.searchNews(any(), any(), any(), any(), any(), any())
        }
    }
}