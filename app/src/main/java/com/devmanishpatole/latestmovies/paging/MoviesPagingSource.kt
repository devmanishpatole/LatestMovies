package com.devmanishpatole.latestmovies.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.devmanishpatole.latestmovies.repositories.MoviesRepository
import com.devmanishpatole.network.models.Movie
import javax.inject.Inject

/**
 * Movies paging source
 *
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 30/06/22
 */
class MoviesPagingSource @Inject constructor(private val repository: MoviesRepository) :
    PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val movieListResponse = repository.getLatestMovies(nextPage)
            var movies = emptyList<Movie>()
            var nextKey: Int? = null

            movieListResponse.onSuccess { moviesResponse ->
                movies = moviesResponse.movies ?: emptyList()
                if (nextPage < moviesResponse.totalPages) {
                    nextKey = nextPage.plus(1)
                }
            }.onFailure {
                throw Exception(it)
            }

            LoadResult.Page(
                data = movies,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = nextKey
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}