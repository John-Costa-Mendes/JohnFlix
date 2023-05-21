package com.johnmendes.johnflix.movies.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johnmendes.johnflix.movies.models.ListResultsMovie
import com.johnmendes.johnflix.movies.models.MovieResponse
import com.johnmendes.johnflix.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesViewModel : ViewModel() {

    enum class MoviesType {
        UPCOMING, POPULAR
    }

    private var movies = MutableLiveData<List<MovieResponse>>()
    private var isLoading = MutableLiveData<Boolean>()
    private var isLayoutError = MutableLiveData<Boolean>()
    private var isLayoutMovies = MutableLiveData<Boolean>()

    fun movies(): LiveData<List<MovieResponse>> {
        return movies
    }

    fun isLoading(): LiveData<Boolean> {
        return isLoading
    }

    fun isLayoutError(): LiveData<Boolean> {
        return isLayoutError
    }

    fun isLayoutMovies(): LiveData<Boolean> {
        return isLayoutMovies
    }

    fun loadMovies(type: MoviesType) {
        val service = RetrofitClient.createMovieService()

        isLoading.value = true

        val call: Call<ListResultsMovie>
        call = if (type == MoviesType.UPCOMING) service.movieUpcoming() else service.moviePopular()
        call.enqueue(object : Callback<ListResultsMovie> {
            override fun onResponse(call: Call<ListResultsMovie>, response: Response<ListResultsMovie>) {
                isLayoutMovies.value = true
                isLoading.value = false
                isLayoutError.value = false
                response.body()?.let { movies.value = it.results }
            }
            override fun onFailure(call: Call<ListResultsMovie>, t: Throwable) {
                isLayoutMovies.value = false
                isLayoutError.value = true
                isLoading.value = false
                Log.e("Error", t.toString())
            }
        })
    }
}