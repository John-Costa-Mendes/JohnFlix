package com.johnmendes.johnflix.movies.service

import com.johnmendes.johnflix.movies.models.ListResultsMovie
import com.johnmendes.johnflix.util.Constants.Companion.API_KEY
import com.johnmendes.johnflix.util.Constants.Companion.LANGUAGE
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

        @GET("movie/popular")
    fun moviePopular(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int = 1,
    ): Call<ListResultsMovie>

    @GET("movie/upcoming")
    fun movieUpcoming(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int = 1,
    ): Call<ListResultsMovie>
}