package com.example.moviedb.datamodel

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("Search")
    val searchResult: List<MovieDataModel>?,
    @SerializedName("Response")
    val response: Boolean,
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("Error")
    val error: String = ""
)

/*
* {"Title":"The Rock","Year":"1996","imdbID":"tt0117500","Type":"movie","Poster":"https://m.media-amazon.com/images/M/MV5BZDJjOTE0N2EtMmRlZS00NzU0LWE0ZWQtM2Q3MWMxNjcwZjBhXkEyXkFqcGdeQXVyNDk3NzU2MTQ@._V1_SX300.jpg"}*/
data class MovieDataModel(
    @SerializedName("Title")
    val title: String = "",
    @SerializedName("Year")
    val year: String = "",
    @SerializedName("imdbID")
    val imbdId: String = "",
    @SerializedName("Type")
    val type: String = "",
    @SerializedName("Poster")
    val posterImage: String = "",
)
