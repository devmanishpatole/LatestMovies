package com.devmanishpatole.latestmovies.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.devmanishpatole.latestmovies.R.string
import com.devmanishpatole.latestmovies.viewmodels.SearchMoviesViewModel
import com.devmanishpatole.network.models.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Entry composable for Movie's screen
 *
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 02/07/22
 */
@Composable
fun MovieSearch(
    viewModel: SearchMoviesViewModel = hiltViewModel(),
    onBackPress: () -> Unit
) {
    MoviesScaffold(
        {
            MovieDetailTitle(
                title = stringResource(string.search)
            )
        },
        onNavigateUp = onBackPress
    ) { modifier ->
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column {
                val movieList by remember {
                    mutableStateOf(viewModel.searchedMovies)
                }

                var queryLength by remember {
                    mutableStateOf(0)
                }

                SearchMovie { query ->
                    if (query.length > 2) {
                        queryLength = query.length
                        viewModel.searchMovies(query)
                    }
                }

                if (queryLength > 2) {
                    SearchMovieList(list = movieList)
                }
            }
        }
    }
}

@Composable
fun SearchMovie(onSearchClick: (String) -> Unit) {
    val focusRequester = remember {
        FocusRequester()
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val focusManager = LocalFocusManager.current
        var text by remember { mutableStateOf(TextFieldValue("")) }
        TextField(
            value = text,
            onValueChange = { newText ->
                onSearchClick(newText.text)
                text = newText
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White
            ),
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ))

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}

@Composable
fun SearchMovieList(list: Flow<PagingData<Movie>>) {
    MovieList(list.collectAsLazyPagingItems()) {}
}