package com.devmanishpatole.latestmovies.ui.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.devmanishpatole.network.models.Movie
import kotlinx.coroutines.flow.Flow

/**
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 02/07/22
 */

/**
 * Displays movie list with pagination
 */
@Composable
fun MovieList(movieList: Flow<PagingData<Movie>>) {
    val movies = movieList.collectAsLazyPagingItems()

    LazyColumn {
        items(movies) { movie ->
            if (movie != null) {
                MovieRow(movie = movie)
            }
        }

        with(movies) {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                }
                loadState.append is LoadState.Loading -> {
                    item { LoadingItem() }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = loadState.refresh as LoadState.Error
                    e.error.localizedMessage?.let { message ->
                        if (message.contains("Internet Unavailable")) {
                            item {
                                ErrorNoInternet(
                                    modifier = Modifier.fillParentMaxSize(),
                                    onClickRetry = { retry() }
                                )
                            }
                        } else {
                            item {
                                ErrorItem(
                                    message = e.error.localizedMessage!!,
                                    modifier = Modifier.fillParentMaxSize(),
                                    onClickRetry = { retry() }
                                )
                            }
                        }
                    }
                }
                loadState.append is LoadState.Error -> {
                    val e = loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            onClickRetry = { retry() }
                        )
                    }
                }
            }
        }
    }
}