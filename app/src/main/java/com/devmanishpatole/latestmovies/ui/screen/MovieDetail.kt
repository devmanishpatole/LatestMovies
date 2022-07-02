package com.devmanishpatole.latestmovies.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest.Builder
import com.devmanishpatole.latestmovies.R.drawable
import com.devmanishpatole.latestmovies.R.string
import com.devmanishpatole.latestmovies.viewmodels.DataSharingViewModel
import com.devmanishpatole.network.BuildConfig

/**
 * Displays movie details
 *
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 02/07/22
 */
@Composable
fun MovieDetail(viewModel: DataSharingViewModel, onBackPress: () -> Unit) {
    viewModel.movie?.let { movie ->
        MoviesScaffold(
            { MovieDetailTitle(title = movie.title ?: stringResource(string.detail)) },
            onNavigateUp = onBackPress
        ) { modifier ->
            Column(modifier = modifier.verticalScroll(rememberScrollState())) {
                AsyncImage(
                    model = Builder(LocalContext.current)
                        .data("${BuildConfig.IMAGE_URL}${movie.backdropPath}")
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(string.description),
                    error = painterResource(drawable.ic_image_error),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                )
                Text(
                    text = movie.title ?: "",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(8.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = drawable.ic_rating),
                            contentDescription = "",
                            Modifier.wrapContentSize()
                        )

                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = movie.voteAverage.toString() + " / 10",
                            style = MaterialTheme.typography.body1
                        )
                    }

                    if (movie.releaseDate != null) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = drawable.ic_calendar),
                                contentDescription = "",
                                Modifier.wrapContentSize()
                            )

                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = movie.releaseDate!!,
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                }

                Text(
                    text = movie.overview ?: "",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}