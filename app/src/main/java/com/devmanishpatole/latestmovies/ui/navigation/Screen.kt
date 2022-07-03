package com.devmanishpatole.latestmovies.ui.navigation

/**
 * Screen keys for navigation graph
 *
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 02/07/22
 */
sealed class Screen(val route: String){
    object Movies : Screen("movies")
    object Detail : Screen("detail")
    object Search : Screen("search")
}
