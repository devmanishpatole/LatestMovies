package com.devmanishpatole.latestmovies.paging

import com.devmanishpatole.core.repositories.Either
import com.devmanishpatole.latestmovies.repositories.MoviesRepository
import com.devmanishpatole.latestmovies.ui.utils.Constant.SEARCH_QUERY_LENGTH
import com.devmanishpatole.network.models.MoviesResponse
import javax.inject.Inject

/**
 * Searched movies paging source
 *
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 30/06/22
 */
class SearchMoviesPagingSource @Inject constructor(
    private val repository: MoviesRepository,
    private val query: String
) : MoviesPagingSource() {

    override suspend fun fetchMovies(nextPage: Int): Either<MoviesResponse> {
        return if (query.length > SEARCH_QUERY_LENGTH) {
            repository.searchMovies(query, nextPage)
        } else {
            Either.error("Query is too small")
        }
    }
}