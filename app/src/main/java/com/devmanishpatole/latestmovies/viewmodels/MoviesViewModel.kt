package com.devmanishpatole.latestmovies.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.devmanishpatole.network.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Movie's ViewModel, bridges the gap between data and UI layer
 *
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 30/06/22
 */
@HiltViewModel
class MoviesViewModel @Inject constructor(
    pager: Pager<Int, Movie>
) : ViewModel() {

    val movies: Flow<PagingData<Movie>> = pager.flow.cachedIn(viewModelScope)
}