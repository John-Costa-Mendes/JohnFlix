package com.johnmendes.johnflix.remote

import com.google.gson.annotations.SerializedName

class ListResultsMovieCredits {

    @SerializedName("cast")
    var castResults = listOf<MovieCreditsResponse>()

}
