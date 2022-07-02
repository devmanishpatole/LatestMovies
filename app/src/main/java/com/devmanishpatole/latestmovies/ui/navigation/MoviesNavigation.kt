package com.devmanishpatole.latestmovies.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.devmanishpatole.latestmovies.ui.screen.MovieDetail
import com.devmanishpatole.latestmovies.ui.screen.MovieLauncher
import com.devmanishpatole.latestmovies.ui.screen.MovieSearch
import com.devmanishpatole.latestmovies.viewmodels.DataSharingViewModel
import com.devmanishpatole.latestmovies.viewmodels.MoviesViewModel

/**
 * Application's navigation graph
 *
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 02/07/22
 */
@Composable
fun MoviesNavigation() {
    val navController = rememberNavController()
    val viewModel: DataSharingViewModel = viewModel()
    val moviesViewModel: MoviesViewModel = hiltViewModel()
    val movieList by remember {
        mutableStateOf(moviesViewModel.movies)
    }
    val list = movieList.collectAsLazyPagingItems()

    NavHost(navController = navController, startDestination = Screen.Movies.route) {
        composable(Screen.Movies.route) {
            MovieLauncher(movieList = list, onSearchClick = {
                navController.navigateToSearch()
            }) { movie ->
                // Important to initialize movie before navigating to detail page
                viewModel.movie = movie
                navController.navigateToDetail()
            }
        }

        composable(Screen.Detail.route) {
            MovieDetail(viewModel) {
                navController.navigateUp()
            }
        }

        composable(Screen.Search.route) {
            MovieSearch {
                navController.navigateUp()
            }
        }
    }
}

/** Navigates to movie detail page */
fun NavController.navigateToDetail() = navigate(Screen.Detail.route)

/** Navigates to search page */
fun NavController.navigateToSearch() = navigate(Screen.Search.route)