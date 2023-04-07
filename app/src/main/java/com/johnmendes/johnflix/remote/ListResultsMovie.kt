package com.johnmendes.johnflix.remote

import com.google.gson.annotations.SerializedName

class ListResultsMovie {

    @SerializedName("results")
    var results = listOf<MovieResponse>()

}