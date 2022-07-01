package com.devmanishpatole.latestmovies.modules

import com.devmanishpatole.latestmovies.repositories.MoviesRemoteRepository
import com.devmanishpatole.latestmovies.repositories.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 30/06/22
 */
@Module
@InstallIn(SingletonComponent::class)
interface MoviesModule {

    @Binds
    fun moviesRepository(repository: MoviesRemoteRepository): MoviesRepository
}