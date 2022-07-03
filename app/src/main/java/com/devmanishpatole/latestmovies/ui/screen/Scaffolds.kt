package com.devmanishpatole.latestmovies.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.devmanishpatole.core.utils.drawableId
import com.devmanishpatole.latestmovies.R

/**
 * Application's Toolbar
 *
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 02/07/22
 */
@Composable
fun MoviesScaffold(
    titleContent: @Composable () -> Unit,
    onNavigateUp: (() -> Unit)? = null,
    screenContent: @Composable (modifier: Modifier) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                title = { titleContent() },
                navigationIcon = onNavigateUp?.let {
                    val navigationIcon: @Composable () -> Unit = {
                        IconButton(
                            modifier = Modifier.padding(start = 4.dp),
                            onClick = onNavigateUp
                        ) {
                            val resId = R.drawable.ic_back
                            Icon(
                                painterResource(resId),
                                "Back",
                                tint = MaterialTheme.colors.onPrimary,
                                modifier = Modifier.semantics { drawableId = resId }
                            )
                        }
                    }
                    navigationIcon
                })

        }) { padding ->
        screenContent(Modifier.padding(padding))
    }
}

@Composable
fun MovieDetailTitle(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title, style = MaterialTheme.typography.h6,
            color = Color.White,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

@Composable
fun MovieTitle(title: String, onSearchClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title, style = MaterialTheme.typography.h6,
            color = Color.White,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )

        IconButton(
            onClick = { onSearchClicked() }
        ) {
            Icon(Icons.Filled.Search, contentDescription = "Search")
        }
    }
}