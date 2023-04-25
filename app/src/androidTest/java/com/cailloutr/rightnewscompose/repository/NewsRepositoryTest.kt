package com.cailloutr.rightnewscompose.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.cailloutr.rightnewscompose.TestCoroutineDispatcher
import com.cailloutr.rightnewscompose.data.local.NewsDatabase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.MockKAnnotations
import io.mockk.junit4.MockKRule
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import javax.inject.Named

@OptIn(ExperimentalCoroutinesApi::class)
@SmallTest
@HiltAndroidTest
class NewsRepositoryTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    /*@MockK
    lateinit var theGuardianApi: TheGuardianApiImpl*/

    private lateinit var repository: NewsRepository
    private lateinit var testDispatcher: TestCoroutineDispatcher

    @Inject
    @Named("test_db")
    lateinit var database: NewsDatabase

    @Before
    fun setUp() {
        hiltRule.inject()
        testDispatcher = TestCoroutineDispatcher()
        MockKAnnotations.init(this, true)
        repository = spyk(
            NewsRepository(
                database
            )
        )
    }

}