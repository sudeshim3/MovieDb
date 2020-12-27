package com.example.openmoviedbswiggy

import com.example.openmoviedbswiggy.datamodel.SearchResult

sealed class MovieResponse {
    data class Data(val result: SearchResult) : MovieResponse()
    data class Error(val error: String) : MovieResponse()
}
