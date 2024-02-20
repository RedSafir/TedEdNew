package com.miftah.tededexpert.core.di

import androidx.room.Room
import com.miftah.tededexpert.core.BuildConfig
import com.miftah.tededexpert.core.data.AppRepositoryImpl
import com.miftah.tededexpert.core.data.source.local.LocalDataSource
import com.miftah.tededexpert.core.data.source.local.room.AppDatabase
import com.miftah.tededexpert.core.data.source.local.room.RemoteKeysDao
import com.miftah.tededexpert.core.data.source.local.room.SaveStoriesDao
import com.miftah.tededexpert.core.data.source.local.room.StoriesDao
import com.miftah.tededexpert.core.data.source.preference.PreferenceDataSourceImpl
import com.miftah.tededexpert.core.data.source.remote.RemoteDataSource
import com.miftah.tededexpert.core.data.source.remote.network.StoryService
import com.miftah.tededexpert.core.domain.preference.PreferenceDataSource
import com.miftah.tededexpert.core.domain.repository.AppRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "Tourism.db"
        ).fallbackToDestructiveMigration().build()
    }
    factory<RemoteKeysDao> {
        get<AppDatabase>().remoteKeysDao()
    }
    factory<SaveStoriesDao> {
        get<AppDatabase>().saveStoriesDao()
    }
    factory<StoriesDao> {
        get<AppDatabase>().storiesDao()
    }
}

val networkModule = module {
    factory<Interceptor> {
        Interceptor { chain ->
            val req = chain.request()

            val token = runBlocking {
                get<PreferenceDataSource>().getSession().first().token
            }

            val requestHeaders = if (token.isNotBlank()) {
                req.newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            } else {
                req
            }
            chain.proceed(requestHeaders)
        }
    }
    factory<HttpLoggingInterceptor> {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
    }
    factory<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(get<Interceptor>())
            .build()
    }
    factory {
        Retrofit
            .Builder()
            .baseUrl("https://story-api.dicoding.dev/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
    }
    single<StoryService> {
        get<Retrofit>().create(StoryService::class.java)
    }
}

val preferenceModule = module {
    single<PreferenceDataSource> {
        PreferenceDataSourceImpl(androidContext())
    }
}

val repositoryModule = module {
    single<LocalDataSource> {
        LocalDataSource(get(), get(), get())
    }
    single<RemoteDataSource> {
        RemoteDataSource(get())
    }
    single<AppRepository> {
        AppRepositoryImpl(get(), get())
    }
}
