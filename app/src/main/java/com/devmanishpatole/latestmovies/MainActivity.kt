package com.devmanishpatole.latestmovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.devmanishpatole.latestmovies.repositories.MoviesRepository
import com.devmanishpatole.latestmovies.ui.screen.MovieList
import com.devmanishpatole.latestmovies.ui.theme.LatestMoviesTheme
import com.devmanishpatole.latestmovies.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
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
                    val movieList by remember {
                        mutableStateOf(viewModel.outputs.movies)
                    }
                    MovieList(movieList)
                }
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