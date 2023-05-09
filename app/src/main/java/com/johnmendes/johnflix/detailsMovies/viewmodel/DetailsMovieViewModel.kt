package com.johnmendes.johnflix.detailsMovies.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johnmendes.johnflix.detailsMovies.models.ListResultsMovieCredits
import com.johnmendes.johnflix.detailsMovies.models.MovieCreditsResponse
import com.johnmendes.johnflix.remote.MovieDetailsResponse
import com.johnmendes.johnflix.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsMovieViewModel(): ViewModel() {

    private val service = RetrofitClient.createDetailsMovieService()
    private var actors = MutableLiveData<List<MovieCreditsResponse>>()
    private var isLoading = MutableLiveData<Boolean>()
    private var details = MutableLiveData<MovieDetailsResponse>()

    fun actors(): LiveData<List<MovieCreditsResponse>> {
        return actors
    }

    fun isLoading(): LiveData<Boolean> {
        return isLoading
    }

    fun details(): LiveData<MovieDetailsResponse> {
        return details
    }

    fun loadActors(movieId: String) {
        isLoading.value = true

        val call : Call<ListResultsMovieCredits> = service.movieCredits(movieId)
        call.enqueue(object : Callback<ListResultsMovieCredits> {
            override fun onResponse(call: Call<ListResultsMovieCredits>, response: Response<ListResultsMovieCredits>) {
                isLoading.value = false
                response.body()?.let { actors.value = it.castResults }
            }
            override fun onFailure(call: Call<ListResultsMovieCredits>, t: Throwable) {
                isLoading.value = false
                Log.e("Error", t.stackTraceToString())
            }
        })
    }

    fun loadDetails(movieId: String){
        val call : Call<MovieDetailsResponse> = service.movieDetails(movieId)
        call.enqueue(object : Callback<MovieDetailsResponse> {
            override fun onResponse(call: Call<MovieDetailsResponse>, response: Response<MovieDetailsResponse>) {
                response.body()?.let { details.value = it }
            }
            override fun onFailure(call: Call<MovieDetailsResponse>, t: Throwable) {
                Log.e("Error", t.stackTraceToString())
            }
        })
    }
}