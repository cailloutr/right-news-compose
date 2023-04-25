package com.cailloutr.rightnewscompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cailloutr.rightnewscompose.data.local.dao.ArticleDao
import com.cailloutr.rightnewscompose.data.local.dao.NewsContainerDao
import com.cailloutr.rightnewscompose.data.local.dao.SectionDao
import com.cailloutr.rightnewscompose.data.local.roommodel.RoomArticle
import com.cailloutr.rightnewscompose.data.local.roommodel.RoomNewsContainer
import com.cailloutr.rightnewscompose.data.local.roommodel.RoomSection

@Database(
    entities = [RoomArticle::class, RoomNewsContainer::class, RoomSection::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase: RoomDatabase() {

    abstract val articleDao: ArticleDao
    abstract val newsContainerDao: NewsContainerDao
    abstract val sectionDao: SectionDao
}