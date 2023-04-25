package com.cailloutr.rightnewscompose.di

import android.content.Context
import androidx.room.Room
import com.cailloutr.rightnewscompose.constants.Constants.TEST_DB
import com.cailloutr.rightnewscompose.data.local.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestModule {

    @Provides
    @Named(TEST_DB)
    fun provideInMemoryDatabase(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(
            context = context,
            klass = NewsDatabase::class.java
        ).allowMainThreadQueries().build()
}