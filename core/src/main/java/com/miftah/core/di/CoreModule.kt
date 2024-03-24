package com.miftah.core.di

import androidx.room.Room
import com.miftah.core.data.AppRepositoryImpl
import com.miftah.core.data.source.local.LocalDataSource
import com.miftah.core.data.source.local.room.AppDatabase
import com.miftah.core.data.source.local.room.RemoteKeysDao
import com.miftah.core.data.source.local.room.SaveStoriesDao
import com.miftah.core.data.source.local.room.StoriesDao
import com.miftah.core.data.source.preference.PreferenceDataSourceImpl
import com.miftah.core.data.source.remote.RemoteDataSource
import com.miftah.core.data.source.remote.network.StoryService
import com.miftah.core.domain.preference.PreferenceDataSource
import com.miftah.core.domain.repository.AppRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    single<AppDatabase> {
        val passphrase : ByteArray = SQLiteDatabase.getBytes("appdatabase".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "appDatabase.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
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
        val hostname = "story-api.dicoding.dev"
        val certificateSPinner = CertificatePinner.Builder()
            .add(hostname, "sha256/W7Dw4CG+jbu1OcLeSKaV63CFGMtj3ncuBKt/5eqNA7k=")
            .add(hostname, "sha256/jQJTbIh0grw0/1TkHSumWb+Fs0Ggogr621gT3PvPKG0=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<Interceptor>())
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificateSPinner)
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
