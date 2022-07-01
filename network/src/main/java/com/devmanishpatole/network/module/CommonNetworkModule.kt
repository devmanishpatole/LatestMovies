package com.devmanishpatole.network.module

import android.content.Context
import com.devmanishpatole.network.BuildConfig.API_KEY
import com.devmanishpatole.network.interceptors.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Provides common resources for networking
 *
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 30/06/22
 */
@Module
@InstallIn(SingletonComponent::class)
object CommonNetworkModule {
    @Singleton
    @Provides
    fun provideOkHttpCache(@ApplicationContext appContext: Context): Cache {
        val cacheSize: Long = 10 * 1024 * 1024 // 10 MB

        return Cache(appContext.cacheDir, cacheSize)
    }

    @Singleton
    @Provides
    fun provideMoshiConverterFactory(): MoshiConverterFactory = MoshiConverterFactory.create()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideApiKeyInterceptor() = ApiKeyInterceptor(API_KEY)
}