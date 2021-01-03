package com.example.openmoviedbswiggy.datamodel

sealed class MovieResponse {
    data class Data(val result: SearchResult) : MovieResponse()
    data class Error(val error: String) : MovieResponse()
}
