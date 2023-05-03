package com.cailloutr.rightnewscompose.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.cailloutr.rightnewscompose.TestCoroutineDispatcher
import com.cailloutr.rightnewscompose.TestsConstants
import com.cailloutr.rightnewscompose.constants.Constants
import com.cailloutr.rightnewscompose.data.local.NewsDatabase
import com.cailloutr.rightnewscompose.data.remote.TheGuardianServiceImpl
import com.cailloutr.rightnewscompose.data.remote.responses.sections.toRoomSections
import com.cailloutr.rightnewscompose.other.Resource
import com.cailloutr.rightnewscompose.repository.NewsRepository
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
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
class GetSectionsUseCaseTest {

    @get:Rule
    val mockRule = MockKRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private lateinit var getSectionsUseCase: GetSectionsUseCase
    private lateinit var testDispatcher: TestCoroutineDispatcher

    lateinit var repository: NewsRepository

    @MockK
    lateinit var service: TheGuardianServiceImpl

    @Inject
    @Named(Constants.TEST_DB)
    lateinit var database: NewsDatabase

    @Before
    fun setUp() {
        hiltRule.inject()
        repository = NewsRepository(
            database, service
        )
        getSectionsUseCase = GetSectionsUseCase(repository)
        testDispatcher = TestCoroutineDispatcher()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun test_invokeShouldReturnAllSections() = runTest {
        val response = TestsConstants.fakeResponseSectionRoot

        coEvery { service.getAllSections() } returns Resource.success(response)

        val result = getSectionsUseCase(testDispatcher.io, {}).first()

        assertThat(result).containsExactlyElementsIn(response.response.results.map { it.toRoomSections() })

    }

    @Test
    fun test_invokeWhenErrorShouldReturnEmptyList() = runTest {
        val response = Resource.error("Error", null)

        coEvery { service.getAllSections() } returns response

        val result = getSectionsUseCase(testDispatcher.io, {}).first()

        assertThat(result).isEmpty()
    }
}