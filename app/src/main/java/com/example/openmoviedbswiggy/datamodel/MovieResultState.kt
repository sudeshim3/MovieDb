package com.example.openmoviedbswiggy.datamodel

sealed class MovieResultState {
    data class PageResult(val pageNumber: Int, val searchResult: SearchResult) : MovieResultState()
    data class PageErrorResponse(val throwable: Throwable) : MovieResultState()
}
