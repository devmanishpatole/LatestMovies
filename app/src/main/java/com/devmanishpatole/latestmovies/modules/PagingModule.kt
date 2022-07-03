package com.devmanishpatole.latestmovies.modules

import androidx.paging.InvalidatingPagingSourceFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.devmanishpatole.network.models.Movie
import com.devmanishpatole.latestmovies.paging.LatestMoviesPagingSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 30/06/22
 */
@InstallIn(SingletonComponent::class)
@Module
object PagingModule {

    @Provides
    fun providePagingConfiguration() = PagingConfig(
        pageSize = 10,
        enablePlaceholders = false,
        initialLoadSize = 40
    )

    @Provides
    fun provideInvalidationFactory(movieSource: LatestMoviesPagingSource) =
        InvalidatingPagingSourceFactory {
            movieSource
        }

    @Provides
    fun providePager(
        sourceFactory: InvalidatingPagingSourceFactory<Int, Movie>,
        pagingConfig: PagingConfig
    ) = Pager(config = pagingConfig, pagingSourceFactory = sourceFactory)
}