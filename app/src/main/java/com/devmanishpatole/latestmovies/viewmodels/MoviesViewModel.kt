package com.devmanishpatole.latestmovies.viewmodels

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.devmanishpatole.network.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 30/06/22
 */
interface MoviesViewModel {
    val outputs: Outputs

    interface Outputs {
        val movies: Flow<PagingData<Movie>>
    }

    @HiltViewModel
    class ViewModel @Inject constructor(
        pager: Pager<Int, Movie>
    ) : androidx.lifecycle.ViewModel(), Outputs, MoviesViewModel {

        override val movies: Flow<PagingData<Movie>> = pager.flow.cachedIn(viewModelScope)

        override val outputs = this
    }
}