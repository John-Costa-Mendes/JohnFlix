package com.johnmendes.johnflix.remote

import com.google.gson.annotations.SerializedName

class MovieCreditsResponse {

    @SerializedName("name")
    var name: String = ""

    @SerializedName ("profile_path")
    var profile_path: String? = ""

    @SerializedName ("character")
    var character: String? = ""


}