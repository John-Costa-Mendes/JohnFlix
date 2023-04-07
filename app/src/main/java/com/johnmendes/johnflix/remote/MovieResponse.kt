package com.johnmendes.johnflix.remote

import com.google.gson.annotations.SerializedName

class MovieResponse {

    @SerializedName ("id")
    var idMovie : Int = 0

    @SerializedName ("poster_path")
    var image: String? = ""

    @SerializedName ("title")
    var title: String = ""

    @SerializedName ("release_date")
    var date: String = ""

}