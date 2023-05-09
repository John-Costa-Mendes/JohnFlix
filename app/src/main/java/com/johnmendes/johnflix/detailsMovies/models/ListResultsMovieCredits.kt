package com.johnmendes.johnflix.detailsMovies.models

import com.google.gson.annotations.SerializedName

class ListResultsMovieCredits {

    @SerializedName("cast")
    var castResults = listOf<MovieCreditsResponse>()

}
