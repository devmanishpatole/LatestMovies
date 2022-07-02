package com.devmanishpatole.latestmovies.ui.screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
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
    val list = moviesViewModel.movies.collectAsLazyPagingItems()

    NavHost(navController = navController, startDestination = Screen.Movies.route) {
        composable(Screen.Movies.route) {
            MovieLauncher(list) {
                viewModel.movie = it
                navController.navigateToDetail()
            }
        }

        composable(Screen.Detail.route) {
            MovieDetail(viewModel) {
                navController.navigateUp()
            }
        }
    }
}

fun NavController.navigateToDetail() = navigate(Screen.Detail.route)