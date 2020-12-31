package com.example.openmoviedbswiggy

import com.example.openmoviedbswiggy.datamodel.SearchResult

sealed class MovieResultState {
    data class PageResult(val pageNumber: Int, val searchResult: SearchResult) : MovieResultState()
    data class PageErrorResponse(val throwable: Throwable) : MovieResultState()
}
