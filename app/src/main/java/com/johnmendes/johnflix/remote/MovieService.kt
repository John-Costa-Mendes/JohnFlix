package com.johnmendes.johnflix.remote

import com.johnmendes.johnflix.util.Constants.Companion.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    fun moviePopular(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "pt-BR"
    ): Call<ListResults>

    /*@GET("movie/upcoming")
    fun movieUpcoming(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "pt-BR"
    ): Call<List<MovieResponse>>

    @GET("movie/{movie_id}")
    fun movieDetails(): Call<List<MovieDetailsResponse>>

    @GET("movie/{movie_id}/credits")
    fun movieCredits(): Call<List<MovieCreditsResponse>>*/
}