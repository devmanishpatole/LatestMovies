package com.devmanishpatole.latestmovies

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.devmanishpatole.latestmovies.ui.theme.LatestMoviesTheme
import com.devmanishpatole.network.repositories.MoviesRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var repository: MoviesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {

            val response = repository.getLatestMovies(1)

            response.onSuccess {
                Toast.makeText(this@MainActivity, "$it.totalPages", Toast.LENGTH_SHORT).show()
            }.onFailure {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
        setContent {
            LatestMoviesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
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