package com.cailloutr.rightnewscompose.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.cailloutr.rightnewscompose.TestCoroutineDispatcher
import com.cailloutr.rightnewscompose.TestsConstants
import com.cailloutr.rightnewscompose.data.local.NewsDatabase
import com.cailloutr.rightnewscompose.data.remote.TheGuardianServiceImpl
import com.cailloutr.rightnewscompose.data.remote.responses.news.NewsFields
import com.cailloutr.rightnewscompose.data.remote.responses.news.NewsResponse
import com.cailloutr.rightnewscompose.data.remote.responses.news.NewsResult
import com.cailloutr.rightnewscompose.data.remote.responses.news.NewsRoot
import com.cailloutr.rightnewscompose.data.remote.responses.news.toNewsContainer
import com.cailloutr.rightnewscompose.data.remote.responses.news.toRoomArticle
import com.cailloutr.rightnewscompose.data.remote.responses.sections.toRoomSections
import com.cailloutr.rightnewscompose.model.SectionWrapper
import com.cailloutr.rightnewscompose.other.Resource
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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

    @MockK
    lateinit var serviceImpl: TheGuardianServiceImpl

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
                database,
                serviceImpl
            )
        )
    }

    @Test

    fun test_getArticleByIdWithAValidIdShouldReturnAMatchingArticle() = runTest {
        val article = NewsResult(
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

        database.articleDao.insertArticle(article)

        val result = repository.getArticleById("games/2023/mar/28/super-mario-lush-soaps2").first()

        assertThat(result).isEqualTo(article)
    }

    @Test
    fun test_getArticleByIdWithInvalidIdShouldReturnNull() = runTest {
        val article = "nonexistent_article_id"

        val result = repository.getArticleById(article).first()

        assertThat(result).isNull()
    }

    @Test
    fun test_getAllSectionsShouldRetrieveSectionsFromDatabase() = runTest {
        val response = TestsConstants.fakeResponseSectionRoot
        coEvery { serviceImpl.getAllSections() } returns (Resource.success(response))

        repository.refreshSections(testDispatcher.io) {}

        val result = repository.getAllSections().first()

        assertThat(result).containsExactlyElementsIn(response.response.results.map { it.toRoomSections() })
    }

    @Test
    fun test_getNewsOfSectionShouldReturnANewsContainer() = runTest {
        val section = SectionWrapper("article", "article")
        val response = TestsConstants.fakeArticle

        coEvery { serviceImpl.getNewsOfSection(section.value) }.returns(Resource.success(response))

        repository.refreshArticles(testDispatcher.io, section) {}

        val result = repository.getNewsOfSection(testDispatcher.io, section).first()

        assertThat(result).isEqualTo(response.response.toNewsContainer(section.sectionName))
    }

    @Test
    fun test_getNewsOfSectionWhenArticleListIsEmpty() = runTest {
        val section = SectionWrapper("NotAValidSectionName", "NotAValidSectionName")
        val response = NewsRoot(
            response = NewsResponse(
                status = "ok",
                userTier = "developer",
                total = 0,
                startIndex = 0,
                pageSize = 10,
                currentPage = 1,
                pages = 0,
                orderBy = "newest",
                results = emptyList()
            )
        )
        coEvery { serviceImpl.getNewsOfSection(section.value) }.returns(Resource.success(response))

        repository.refreshArticles(testDispatcher.io, section) {}

        val result = repository.getNewsOfSection(testDispatcher.io, section).first()

        assertThat(result.results).isEmpty()
    }

    @Test
    fun test_fetchSectionsFromApiShouldUpdateDatabase() = runTest {
        val response =
            TestsConstants.fakeResponseSectionRoot

        coEvery { serviceImpl.getAllSections() } returns (Resource.success(response))

        repository.fetchSectionsFromApi(testDispatcher.io) {}

        val sections = database.sectionDao.getAllSection().first()

        assertThat(sections).containsExactlyElementsIn(response.response.results.map { it.toRoomSections() })
    }

    @Test
    fun test_fetchSectionsFromApiShouldWhenResponseIsErrorShouldNotUpdateDatabase() = runTest {
        coEvery { serviceImpl.getAllSections() } returns (Resource.error(
            msg = "Error",
            data = null
        ))

        repository.fetchSectionsFromApi(testDispatcher.io) {}

        val sections = database.sectionDao.getAllSection().first()

        assertThat(sections).isEmpty()
    }

    @Test
    fun test_refreshSectionsShouldCallFetchSectionsFromApi() = runTest {
        val mockkRepository = spyk(
            NewsRepository(
                database,
                serviceImpl
            )
        )

        coEvery { serviceImpl.getAllSections() }.returns(Resource.success(null))

        mockkRepository.refreshSections(testDispatcher.io) {}

        coVerify { mockkRepository.fetchSectionsFromApi(testDispatcher.io, any()) }
    }

    @Test
    fun test_refreshArticlesShouldCallFetchArticlesFromApi() = runTest {
        val section = SectionWrapper("", "")
        val mockkRepository = spyk(
            NewsRepository(
                database,
                serviceImpl
            )
        )

        coEvery { serviceImpl.getNewsOfSection(section.value) }.returns(Resource.success(null))

        mockkRepository.refreshArticles(testDispatcher.io, section) {}

        coVerify { mockkRepository.fetchArticlesFromApi(testDispatcher.io, section, any()) }
    }

    @Test
    fun test_fetchArticlesFromApiShouldUpdateDatabase() = runTest {
        val section = SectionWrapper("news", "news")
        val response =
            TestsConstants.fakeArticle

        coEvery { serviceImpl.getNewsOfSection(section.value) } returns (Resource.success(response))

        repository.fetchArticlesFromApi(testDispatcher.io, section) {}

        val container =
            database.newsContainerDao.getNewsContainer(section.sectionName).first()
        val sections =
            database.articleDao.getAllArticlesFromSection(section.sectionName).first()

        assertThat(container?.currentPage).isEqualTo(response.response.currentPage)
        assertThat(container?.orderBy).isEqualTo(response.response.orderBy)
        assertThat(container?.pages).isEqualTo(response.response.pages)
        assertThat(container?.pageSize).isEqualTo(response.response.pageSize)
        assertThat(container?.startIndex).isEqualTo(response.response.startIndex)
        assertThat(container?.total).isEqualTo(response.response.total)

        assertThat(sections).containsExactlyElementsIn(response.response.results.map {
            it.toRoomArticle(
                container?.id!!
            )
        })
    }

    @Test
    fun test_fetchArticlesFromApiShouldWhenResponseIsErrorShouldNotUpdateDatabase() = runTest {
        val section = SectionWrapper("news", "news")
        coEvery { serviceImpl.getNewsOfSection(section.value) } returns (Resource.error(
            msg = "Error",
            data = null
        ))

        repository.fetchArticlesFromApi(testDispatcher.io, section) {}

        val sections = database.articleDao.getAllArticlesFromSection(section.sectionName).first()

        assertThat(sections).isEmpty()
    }

    @Test
    fun test_cleanCacheShouldDeleteAllArticlesFromSection() = runTest {
        val section = SectionWrapper("news", "news")
        val response =
            TestsConstants.fakeArticle

        coEvery { serviceImpl.getNewsOfSection(section.value) } returns (Resource.success(response))

        repository.fetchArticlesFromApi(testDispatcher.io, section) {}

        val container =
            database.newsContainerDao.getNewsContainer(section.sectionName).first()
        var sections =
            database.articleDao.getAllArticlesFromSection(section.sectionName).first()

        assertThat(container?.currentPage).isEqualTo(response.response.currentPage)
        assertThat(container?.orderBy).isEqualTo(response.response.orderBy)
        assertThat(container?.pages).isEqualTo(response.response.pages)
        assertThat(container?.pageSize).isEqualTo(response.response.pageSize)
        assertThat(container?.startIndex).isEqualTo(response.response.startIndex)
        assertThat(container?.total).isEqualTo(response.response.total)

        assertThat(sections).containsExactlyElementsIn(response.response.results.map {
            it.toRoomArticle(
                container?.id!!
            )
        })

        repository.cleanCache(testDispatcher.io, section.sectionName)

        sections =
            database.articleDao.getAllArticlesFromSection(section.sectionName).first()

        assertThat(sections).isEmpty()


    }

}