package com.johnmendes.johnflix.detailsMovies

import com.google.gson.annotations.SerializedName

class MovieCreditsResponse {

    @SerializedName("name")
    var name: String = ""

    @SerializedName ("profile_path")
    var image: String? = ""

    @SerializedName ("character")
    var character: String = ""

}