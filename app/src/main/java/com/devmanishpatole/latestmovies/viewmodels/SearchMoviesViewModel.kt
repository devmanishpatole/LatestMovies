package com.devmanishpatole.latestmovies.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.InvalidatingPagingSourceFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.devmanishpatole.latestmovies.paging.SearchMoviesPagingSource
import com.devmanishpatole.latestmovies.repositories.MoviesRepository
import com.devmanishpatole.latestmovies.ui.utils.Constant.SEARCH_QUERY_LENGTH
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
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
    private val searchQuery = MutableStateFlow("")

    companion object{
        private const val WAIT_PERIOD : Long = 500
    }

    init {
        viewModelScope.launch {
            searchQuery
                .debounce(WAIT_PERIOD)
                .distinctUntilChanged()
                .filter { it.length > SEARCH_QUERY_LENGTH }
                .collect { query ->
                    searchKeyWord = query
                    searchInvalidateFactory.invalidate()
                }
        }
    }

    private val searchInvalidateFactory = InvalidatingPagingSourceFactory {
        SearchMoviesPagingSource(repository, searchKeyWord)
    }

    // Defining separate pager for searched result
    val searchedMovies = Pager(
        config = config,
        pagingSourceFactory = searchInvalidateFactory
    ).flow.cachedIn(viewModelScope)

    fun searchMovies(query: String) {
        searchQuery.value = query
    }
}