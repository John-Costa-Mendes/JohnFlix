package com.johnmendes.johnflix.remote

import com.google.gson.annotations.SerializedName

class MovieDetailsResponse() {

    @SerializedName("backdrop_path")
    var posterPath : String = ""

    @SerializedName("title")
    var title: String = ""

    @SerializedName("genres")
    var genres: List<Genre> = listOf()

    @SerializedName("release_date")
    var releaseDate: String = ""

    @SerializedName("runtime")
    var runtime: String = ""

    @SerializedName("overview")
    var overview: String = ""

}

class Genre() {

    @SerializedName("name")
    var name: String = ""
}