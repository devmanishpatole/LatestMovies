package com.devmanishpatole.network.repositories

import com.devmanishpatole.core.repositories.Either
import com.devmanishpatole.latestmovies.repositories.MoviesRepository
import com.devmanishpatole.network.models.MoviesResponse
import com.devmanishpatole.network.services.MoviesService
import com.devmanishpatole.network.util.getResponse
import javax.inject.Inject

/**
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 30/06/22
 */
class MoviesRemoteRepository @Inject constructor(private val service: MoviesService) :
    MoviesRepository {

    /**
     * Provide latest movie list
     */
    override suspend fun getLatestMovies(page: Int): Either<MoviesResponse> {
        return runCatching {
            Either.success(service.getNowPlayingMovies(page).getResponse())
        }.getOrElse {
            Either.error(it.message ?: "Error Occured")
        }
    }
}