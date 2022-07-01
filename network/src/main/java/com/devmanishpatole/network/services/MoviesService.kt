package com.devmanishpatole.network.services

import com.devmanishpatole.network.models.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Network API interfaces
 *
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 30/06/22
 */
interface MoviesService {

    @GET("now_playing")
    suspend fun getNowPlayingMovies(@Query("page") page: Int) : Response<MoviesResponse>
}