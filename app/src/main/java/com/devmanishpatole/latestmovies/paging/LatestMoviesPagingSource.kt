package com.devmanishpatole.latestmovies.paging

import com.devmanishpatole.core.repositories.Either
import com.devmanishpatole.latestmovies.repositories.MoviesRepository
import com.devmanishpatole.network.models.MoviesResponse
import javax.inject.Inject

/**
 * Latest movies paging source
 *
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 30/06/22
 */
class LatestMoviesPagingSource @Inject constructor(private val repository: MoviesRepository) :
    MoviesPagingSource() {

    override suspend fun fetchMovies(nextPage: Int): Either<MoviesResponse> {
        return repository.getLatestMovies(nextPage)
    }
}