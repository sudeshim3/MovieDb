package com.example.moviedb

import com.example.moviedb.datamodel.SearchResult

sealed class MovieResultState {
    data class PageResult(val pageNumber: Int, val searchResult: SearchResult) : MovieResultState()
    data class PageErrorResponse(val throwable: Throwable) : MovieResultState()
}
