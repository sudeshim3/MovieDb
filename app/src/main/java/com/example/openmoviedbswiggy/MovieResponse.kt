package com.example.openmoviedbswiggy

import com.example.openmoviedbswiggy.datamodel.SearchResult

sealed class MovieResponse {
    data class Data(val result: SearchResult) : MovieResponse()
    object Loading : MovieResponse()
    data class Error(val error: String) : MovieResponse()
}
