package com.johnmendes.johnflix.movies.models

import com.google.gson.annotations.SerializedName
import com.johnmendes.johnflix.movies.models.MovieResponse

class ListResultsMovie {

    @SerializedName("results")
    var results = listOf<MovieResponse>()

}