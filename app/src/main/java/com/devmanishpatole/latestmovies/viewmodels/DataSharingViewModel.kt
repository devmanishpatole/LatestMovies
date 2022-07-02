package com.devmanishpatole.latestmovies.viewmodels

import androidx.lifecycle.ViewModel
import com.devmanishpatole.network.models.Movie

/**
 * Shares movie detail between compositions
 *
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 02/07/22
 */
class DataSharingViewModel: ViewModel() {
    // Clicked movie detail
    var movie: Movie? = null
}