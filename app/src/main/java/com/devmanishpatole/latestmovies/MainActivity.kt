package com.devmanishpatole.latestmovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.devmanishpatole.latestmovies.ui.theme.LatestMoviesTheme
import com.devmanishpatole.network.models.Movie
import com.devmanishpatole.latestmovies.repositories.MoviesRepository
import com.devmanishpatole.latestmovies.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var repository: MoviesRepository

    private val viewModel: MoviesViewModel by viewModels<MoviesViewModel.ViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LatestMoviesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    Greeting("Android")
                    val movieList by remember {
                        // Flow of orders
                        mutableStateOf(viewModel.outputs.movies)
                    }
                    ShowMovies(movies = movieList)
                }
            }
        }
    }
}

@Composable
fun ShowMovies(
    movies: Flow<PagingData<Movie>>
) {
    val movieList = movies.collectAsLazyPagingItems()

    LazyColumn {
        items(movieList) { movie ->
            Row {
                Text(modifier = Modifier.padding(4.dp), text = movie?.title ?: "NULL")
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LatestMoviesTheme {
        Greeting("Android")
    }
}