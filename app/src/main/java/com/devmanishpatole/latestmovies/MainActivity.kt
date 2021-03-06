package com.devmanishpatole.latestmovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.devmanishpatole.latestmovies.ui.navigation.MoviesNavigation
import com.devmanishpatole.latestmovies.ui.theme.LatestMoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            LatestMoviesTheme {
                MoviesNavigation()
            }
        }
    }
}