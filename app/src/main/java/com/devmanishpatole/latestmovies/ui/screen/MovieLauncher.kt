package com.devmanishpatole.latestmovies.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.LazyPagingItems
import com.devmanishpatole.latestmovies.R.string
import com.devmanishpatole.network.models.Movie

/**
 * Entry composable for Movie's screen
 *
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 02/07/22
 */
@Composable
fun MovieLauncher(
    movieList: LazyPagingItems<Movie>,
    onSearchClick: () -> Unit,
    onMovieSelect: (Movie) -> Unit
) {

    MoviesScaffold(
        {
            MovieTitle(
                title = stringResource(string.movies),
                onSearchClicked = onSearchClick
            )
        }
    ) { modifier ->
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            MovieList(movieList) {
                onMovieSelect(it)
            }
        }
    }
}