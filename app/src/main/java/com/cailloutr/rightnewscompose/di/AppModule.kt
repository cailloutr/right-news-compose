package com.cailloutr.rightnewscompose.di

import android.content.Context
import androidx.room.Room
import com.cailloutr.rightnewscompose.constants.Constants.DATABASE_NAME
import com.cailloutr.rightnewscompose.data.local.NewsDatabase
import com.cailloutr.rightnewscompose.other.DefaultDispatchers
import com.cailloutr.rightnewscompose.other.DispatchersProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context = context,
            NewsDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideDispatchersProvider(dispatchers: DefaultDispatchers): DispatchersProvider =
        dispatchers
}