package com.devmanishpatole.latestmovies.utils

import com.devmanishpatole.network.models.Movie

/**
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 03/07/22
 */
object FakeData {

    fun getFakeMovie(
        id: Int = 1,
        originalTitle: String = "Fake Movie",
        overview: String = "Fake Overview",
        posterPath: String = "Fake Posterpath",
        backdropPath: String = "Fake BackdropPath",
        releaseDate: String = "Fake release date",
        title: String = "Fake title",
        voteAverage: Double = 8.0
    ) = Movie(
        id,
        originalTitle,
        overview,
        posterPath,
        backdropPath,
        releaseDate,
        title,
        voteAverage
    )
}