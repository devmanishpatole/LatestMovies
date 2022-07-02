package com.devmanishpatole.latestmovies.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.InvalidatingPagingSourceFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.devmanishpatole.latestmovies.paging.SearchMoviesPagingSource
import com.devmanishpatole.latestmovies.repositories.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Search Movie's ViewModel, bridges the gap between data and UI layer
 *
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 03/07/22
 */
@HiltViewModel
class SearchMoviesViewModel @Inject constructor(
    config: PagingConfig,
    private val repository: MoviesRepository
) : ViewModel() {

    private var searchKeyWord: String = ""

    private val searchInvalidateFactory = InvalidatingPagingSourceFactory {
        SearchMoviesPagingSource(repository, searchKeyWord)
    }

    // Defining separate pager for searched result
    val searchedMovies = Pager(
        config = config,
        pagingSourceFactory = searchInvalidateFactory
    ).flow.cachedIn(viewModelScope)

    fun searchMovies(query: String) {
        searchKeyWord = query
        searchInvalidateFactory.invalidate()
    }
}