package com.devmanishpatole.latestmovies.repositories

import com.devmanishpatole.core.repositories.Either
import com.devmanishpatole.core.utils.NetworkManager
import com.devmanishpatole.network.models.MoviesResponse
import com.devmanishpatole.network.services.MoviesService
import com.devmanishpatole.network.util.getResponse
import javax.inject.Inject

/**
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 30/06/22
 */
class MoviesRemoteRepository @Inject constructor(
    private val service: MoviesService,
    private val networkManager: NetworkManager
) : MoviesRepository {
    /**
     * Provide latest movie list
     */
    override suspend fun getLatestMovies(page: Int): Either<MoviesResponse> {
        return runCatching {
            if (networkManager.isConnected) {
                Either.success(service.getNowPlayingMovies(page).getResponse())
            } else {
                Either.error("Internet Unavailable")
            }
        }.getOrElse {
            Either.error(it.message ?: "Something went wrong!")
        }
    }

    override suspend fun searchMovies(query: String, page: Int): Either<MoviesResponse> {
        return runCatching {
            if (networkManager.isConnected) {
                Either.success(service.searchMovies(query, page).getResponse())
            } else {
                Either.error("Internet Unavailable")
            }
        }.getOrElse {
            Either.error(it.message ?: "Something went wrong!")
        }
    }
}