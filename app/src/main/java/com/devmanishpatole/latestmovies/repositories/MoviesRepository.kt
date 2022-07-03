package com.devmanishpatole.latestmovies.repositories

import com.devmanishpatole.core.repositories.Either
import com.devmanishpatole.network.models.MoviesResponse

/**
 * Provides movies API interface
 *
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 30/06/22
 */
interface MoviesRepository {
    /**
     * Provide latest movie list
     */
    suspend fun getLatestMovies(page: Int): Either<MoviesResponse>

    /**
     * Search movie
     */
    suspend fun searchMovies(query: String, page: Int): Either<MoviesResponse>
}