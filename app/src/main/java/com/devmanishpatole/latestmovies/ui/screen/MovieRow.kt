package com.devmanishpatole.latestmovies.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.devmanishpatole.latestmovies.R
import com.devmanishpatole.latestmovies.ui.utils.TestTag.MOVIE_ROW
import com.devmanishpatole.network.BuildConfig.IMAGE_URL
import com.devmanishpatole.network.models.Movie

/**
 * Display movie detail in list
 *
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 01/07/22
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieRow(movie: Movie, onMovieSelect: (Movie) -> Unit) {
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .padding(8.dp)
            .wrapContentHeight()
            .testTag(MOVIE_ROW),
        onClick = { onMovieSelect(movie) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("$IMAGE_URL${movie.posterPath}")
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.description),
                error = painterResource(R.drawable.ic_broken_link),
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .height(200.dp)
                    .width(135.dp)
            )

            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = movie.title ?: "", style = MaterialTheme.typography.h6)
                Text(
                    text = movie.overview ?: "",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(top = 8.dp),
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}