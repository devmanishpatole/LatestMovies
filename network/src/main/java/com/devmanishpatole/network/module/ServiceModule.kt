package com.devmanishpatole.network.module

import com.devmanishpatole.network.services.MoviesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Provides retrofit services
 *
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 30/06/22
 */
@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideMoviesService(retrofit: Retrofit) = retrofit.create(MoviesService::class.java)

}