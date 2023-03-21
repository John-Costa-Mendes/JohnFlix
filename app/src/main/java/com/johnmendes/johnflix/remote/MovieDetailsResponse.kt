package com.johnmendes.johnflix.remote

import com.google.gson.annotations.SerializedName

class MovieDetailsResponse() {

    @SerializedName("poster_path")
    var poster_path : String? = ""

    @SerializedName("title")
    var title: String = ""

    @SerializedName("title")
    var genres: List<String> = listOf()

    @SerializedName("release_date")
    var release_date: String = ""

    @SerializedName("runtime")
    var runtime: Int = 0

    @SerializedName("overview")
    var overview: String? = ""
}