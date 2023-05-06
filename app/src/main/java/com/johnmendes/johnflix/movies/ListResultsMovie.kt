package com.johnmendes.johnflix.movies

import com.google.gson.annotations.SerializedName

class ListResultsMovie {

    @SerializedName("results")
    var results = listOf<MovieResponse>()

}