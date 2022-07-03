package com.devmanishpatole.latestmovies.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.devmanishpatole.core.repositories.Either
import com.devmanishpatole.network.models.Movie
import com.devmanishpatole.network.models.MoviesResponse

/**
 * Movies paging source
 *
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 03/07/22
 */
abstract class MoviesPagingSource : PagingSource<Int, Movie>(){

    abstract suspend fun fetchMovies(nextPage : Int) : Either<MoviesResponse>

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val movieListResponse = fetchMovies(nextPage)
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