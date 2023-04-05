package com.johnmendes.johnflix.remote

import com.johnmendes.johnflix.util.Constants
import com.johnmendes.johnflix.util.Constants.Companion.API_KEY
import com.johnmendes.johnflix.util.Constants.Companion.LANGUAGE
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

        @GET("movie/popular")
    fun moviePopular(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE
    ): Call<ListResultsMovie>

    @GET("movie/upcoming")
    fun movieUpcoming(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE
    ): Call<ListResultsMovie>

    @GET("movie/{movie_id}")
    fun movieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = Constants.LANGUAGE
    ): Call<MovieDetailsResponse>

    @GET("movie/{movie_id}/credits")
    fun movieCredits(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE
    ): Call<ListResultsMovieCredits>

}