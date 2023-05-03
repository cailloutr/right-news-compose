package com.cailloutr.rightnewscompose.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.cailloutr.rightnewscompose.TestCoroutineDispatcher
import com.cailloutr.rightnewscompose.constants.Constants.TEST_DB
import com.cailloutr.rightnewscompose.data.local.NewsDatabase
import com.cailloutr.rightnewscompose.data.remote.TheGuardianServiceImpl
import com.cailloutr.rightnewscompose.data.remote.responses.news.NewsFields
import com.cailloutr.rightnewscompose.data.remote.responses.news.NewsResult
import com.cailloutr.rightnewscompose.data.remote.responses.news.toRoomArticle
import com.cailloutr.rightnewscompose.repository.NewsRepository
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@Suppress("OPT_IN_USAGE")
@HiltAndroidTest
@SmallTest
class GetArticleByIdUseCaseTest {

    @get:Rule
    val mockRule = MockKRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private lateinit var getArticleByIdUseCase: GetArticleByIdUseCase
    private lateinit var testDispatcher: TestCoroutineDispatcher

    lateinit var repository: NewsRepository

    @MockK
    lateinit var service: TheGuardianServiceImpl

    @Inject
    @Named(TEST_DB)
    lateinit var database: NewsDatabase


    @Before
    fun setUp() {
        hiltRule.inject()
        repository = NewsRepository(
            database, service
        )
        getArticleByIdUseCase = GetArticleByIdUseCase(repository)
        testDispatcher = TestCoroutineDispatcher()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun test_invokeShouldReturnArticleFromDatabase() = runTest {
        val response = NewsResult(
            id = "games/2023/mar/28/super-mario-lush-soaps2",
            type = "article",
            sectionId = "news",
            sectionName = "Article",
            webPublicationDate = "2023-03-28T12:15:18Z",
            webTitle = "Luigi has sweet notes of apple’: testing out Lush’s unlikely Super Mario soaps",
            webUrl = "https://www.theguardian.com/games/2023/mar/28/super-mario-lush-soaps",
            apiUrl = "https://content.guardianapis.com/games/2023/mar/28/super-mario-lush-soaps",
            fields = NewsFields(
                headline = "Luigi has sweet notes of apple’: testing out Lush’s unlikely Super Mario soaps",
                trailText = "Animal-friendly cosmetics brand Lush is releasing a range of Mario-themed products – so our reporter tried them, for science",
                thumbnail = "https://media.guim.co.uk/c6436ebbf6e5eceee60a496698e4fc5004c176db/0_327_2000_1200/500.jpg",
                body = "Luigi has sweet notes of apple’: testing out Lush’s unlikely Super Mario soaps"
            ),
            isHosted = false,
            pillarId = "pillar/arts",
            pillarName = "Arts"
        ).toRoomArticle("teste")

        database.articleDao.insertArticle(response)

        val result = getArticleByIdUseCase(response.id).first()

        assertThat(result).isEqualTo(response)
    }

    @Test
    fun test_invokeWithInvalidIdShouldReturnNull() = runTest {
        val response = NewsResult(
            id = "games/2023/mar/28/super-mario-lush-soaps2",
            type = "article",
            sectionId = "news",
            sectionName = "Article",
            webPublicationDate = "2023-03-28T12:15:18Z",
            webTitle = "Luigi has sweet notes of apple’: testing out Lush’s unlikely Super Mario soaps",
            webUrl = "https://www.theguardian.com/games/2023/mar/28/super-mario-lush-soaps",
            apiUrl = "https://content.guardianapis.com/games/2023/mar/28/super-mario-lush-soaps",
            fields = NewsFields(
                headline = "Luigi has sweet notes of apple’: testing out Lush’s unlikely Super Mario soaps",
                trailText = "Animal-friendly cosmetics brand Lush is releasing a range of Mario-themed products – so our reporter tried them, for science",
                thumbnail = "https://media.guim.co.uk/c6436ebbf6e5eceee60a496698e4fc5004c176db/0_327_2000_1200/500.jpg",
                body = "Luigi has sweet notes of apple’: testing out Lush’s unlikely Super Mario soaps"
            ),
            isHosted = false,
            pillarId = "pillar/arts",
            pillarName = "Arts"
        ).toRoomArticle("teste")

        database.articleDao.insertArticle(response)

        val result = getArticleByIdUseCase("invalidId").first()

        assertThat(result).isNull()
    }
}