package com.example.moviedb

import com.example.moviedb.datamodel.SearchResult

sealed class MovieResponse {
    data class Data(val result: SearchResult) : MovieResponse()
    data class Error(val error: String) : MovieResponse()
}
