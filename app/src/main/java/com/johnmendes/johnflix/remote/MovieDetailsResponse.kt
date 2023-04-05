package com.johnmendes.johnflix.remote

import com.google.gson.annotations.SerializedName

class MovieDetailsResponse() {

    @SerializedName("poster_path")
    var posterPath : String? = ""

    @SerializedName("title")
    var title: String = ""

    @SerializedName("genres")
    var genres: List<String> = listOf()

    @SerializedName("release_date")
    var releaseDate: String = ""

    @SerializedName("runtime")
    var runtime: Int = 0

    @SerializedName("overview")
    var overview: String? = ""

}