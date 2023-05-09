package com.johnmendes.johnflix.detailsMovies.service

import com.johnmendes.johnflix.detailsMovies.models.ListResultsMovieCredits
import com.johnmendes.johnflix.remote.MovieDetailsResponse
import com.johnmendes.johnflix.util.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailsMovieApiService {

    @GET("movie/{movie_id}")
    fun movieDetails(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = Constants.LANGUAGE
    ): Call<MovieDetailsResponse>

    @GET("movie/{movie_id}/credits")
    fun movieCredits(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = Constants.LANGUAGE
    ): Call<ListResultsMovieCredits>

}