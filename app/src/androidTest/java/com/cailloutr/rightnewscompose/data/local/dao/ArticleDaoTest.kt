package com.cailloutr.rightnewscompose.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.cailloutr.rightnewscompose.constants.Constants.ROOM_NEWS_CONTAINER_DEFAULT_SECTION
import com.cailloutr.rightnewscompose.data.local.NewsDatabase
import com.cailloutr.rightnewscompose.data.local.roommodel.RoomArticle
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
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
@SmallTest
@HiltAndroidTest
class ArticleDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: NewsDatabase

    lateinit var dao: ArticleDao

    lateinit var article: RoomArticle

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.articleDao
        article = RoomArticle(
            id = "test",
            type = "test",
            sectionId = "test",
            sectionName = "test",
            webPublicationDate = "24/12/24",
            webTitle = "Teste",
            webUrl = "www.test.com",
            apiUrl = "www.api.test.com",
            isHosted = false,
            pillarId = "teste",
            pillarName = "test",
            trailText = "Teste",
            thumbnail = "teste",
            headline = "Test",
            body = "Teste",
            containerId = ROOM_NEWS_CONTAINER_DEFAULT_SECTION
        )
        database.clearAllTables()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun test_insertArticle() = runTest {
        dao.insertArticle(article)

        val retrievedArticle: List<RoomArticle> =
            dao.getAllArticlesFromSection(ROOM_NEWS_CONTAINER_DEFAULT_SECTION).first()

        assertThat(retrievedArticle).contains(article)
    }

    @Test
    fun test_deleteArticle() = runTest {
        dao.insertArticle(article)

        var retrievedArticle: List<RoomArticle> = dao.getAllArticlesFromSection(ROOM_NEWS_CONTAINER_DEFAULT_SECTION).first()

        assertThat(retrievedArticle).contains(article)

        dao.deleteArticle(article)

        retrievedArticle = dao.getAllArticlesFromSection(ROOM_NEWS_CONTAINER_DEFAULT_SECTION).first()

        assertThat(retrievedArticle).doesNotContain(article)
    }

    @Test
    fun test_getArticle() = runTest {
        dao.insertArticle(article)

        val retrieveArticles = dao.getArticle(article.id).first()

        assertThat(retrieveArticles?.id).isEqualTo(article.id)
    }
}