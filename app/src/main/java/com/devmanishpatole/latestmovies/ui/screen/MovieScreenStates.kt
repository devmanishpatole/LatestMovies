package com.devmanishpatole.latestmovies.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.devmanishpatole.latestmovies.R

/**
 * Various screen state's while paginating the movies list
 *
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 02/07/22
 */

@Composable
fun LoadingView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}


@Composable
fun LoadingItem() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}


@Composable
fun ErrorNoInternet(
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.no_internet),
            contentDescription = "",
            Modifier.padding(4.dp)
        )
        Text(
            modifier = Modifier.padding(4.dp),
            text = stringResource(R.string.oops),
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.padding(4.dp),
            text = stringResource(R.string.no_internet),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
        OutlinedButton(
            onClick = onClickRetry,
            modifier = Modifier.padding(4.dp)
        ) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Composable
fun ErrorItem(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            maxLines = 1,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.h6
        )
        OutlinedButton(onClick = onClickRetry) {
            Text(text = stringResource(R.string.try_again))
        }
    }
}