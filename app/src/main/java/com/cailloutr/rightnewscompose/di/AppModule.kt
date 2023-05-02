package com.cailloutr.rightnewscompose.di

import android.content.Context
import androidx.room.Room
import com.cailloutr.rightnewscompose.constants.Constants.DATABASE_NAME
import com.cailloutr.rightnewscompose.data.local.NewsDatabase
import com.cailloutr.rightnewscompose.data.remote.TheGuardianService
import com.cailloutr.rightnewscompose.data.remote.TheGuardianServiceImpl
import com.cailloutr.rightnewscompose.other.DefaultDispatchers
import com.cailloutr.rightnewscompose.other.DispatchersProvider
import com.cailloutr.rightnewscompose.repository.NewsRepository
import com.cailloutr.rightnewscompose.usecases.GetArticleByIdUseCase
import com.cailloutr.rightnewscompose.usecases.GetNewsBySectionUseCase
import com.cailloutr.rightnewscompose.usecases.GetSectionsUseCase
import com.cailloutr.rightnewscompose.usecases.NewsUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 30000L
                connectTimeoutMillis = 30000L
                socketTimeoutMillis = 30000L
            }
        }
    }

    @Provides
    @Singleton
    fun provideApiService(httpClient: HttpClient): TheGuardianService =
        TheGuardianServiceImpl(httpClient)


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

    @Provides
    fun providesNewsUseCases(repository: NewsRepository): NewsUseCases {
        return NewsUseCases(
            GetSectionsUseCase(repository),
            GetNewsBySectionUseCase(repository),
            GetArticleByIdUseCase(repository)
        )
    }
}