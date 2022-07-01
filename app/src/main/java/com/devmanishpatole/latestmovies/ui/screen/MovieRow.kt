package com.devmanishpatole.latestmovies.ui.screen

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.devmanishpatole.latestmovies.R
import com.devmanishpatole.network.BuildConfig.IMAGE_URL
import com.devmanishpatole.network.models.Movie

/**
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 01/07/22
 */

@Composable
fun MovieRow(movie: Movie) {
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .padding(8.dp)
            .wrapContentHeight()
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
                contentScale = ContentScale.Fit,
                modifier = Modifier.height(200.dp)
            )

            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = movie.title ?: "", style = MaterialTheme.typography.h6)
                Text(
                    text = movie.overview ?: "",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(top = 8.dp),
                    maxLines = 5, overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun LottieAnimation(
    @RawRes resId: Int,
    modifier: Modifier = Modifier,
    iterations: Int = LottieConstants.IterateForever,
    restartOnPlay: Boolean = true
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(resId))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = iterations,
        restartOnPlay = restartOnPlay
    )

    com.airbnb.lottie.compose.LottieAnimation(composition, progress = {
        progress
    }, modifier = modifier)
}