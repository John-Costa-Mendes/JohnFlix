package com.johnmendes.johnflix.remote

import com.johnmendes.johnflix.detailsMovies.service.DetailsMovieApiService
import com.johnmendes.johnflix.movies.service.MovieApiService
import com.johnmendes.johnflix.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient (){

    companion object {
        private lateinit var INSTANCE: Retrofit

        private fun getRetrofitInstance(): Retrofit {
            val http = OkHttpClient.Builder()
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(http.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return INSTANCE
        }

        fun createMovieService(): MovieApiService {
            return getRetrofitInstance().create(MovieApiService::class.java)
        }

        fun createDetailsMovieService(): DetailsMovieApiService {
            return getRetrofitInstance().create(DetailsMovieApiService::class.java)
        }
    }
}