package com.johnmendes.johnflix.remote

import com.google.gson.annotations.SerializedName

class MovieResponse {

    @SerializedName ("id")
    var id : Int = 0

    @SerializedName ("poster_path")
    var poster_path: String? = ""

    @SerializedName ("title")
    var title: String = ""

    @SerializedName ("release_date")
    var release_date: String = ""



}