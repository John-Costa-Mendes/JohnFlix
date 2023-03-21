package com.johnmendes.johnflix.remote

import com.google.gson.annotations.SerializedName

class ListResults {

    @SerializedName("results")
    var results = listOf<MovieResponse>()
}