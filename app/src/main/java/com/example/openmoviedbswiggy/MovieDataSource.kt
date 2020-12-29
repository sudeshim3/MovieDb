package com.example.openmoviedbswiggy

import com.example.openmoviedbswiggy.datamodel.Result
import com.example.openmoviedbswiggy.datamodel.SearchResult

interface MovieDataSource {

    suspend fun fetchMovies(searchString: String, pageNumber: Int): Result<SearchResult>
}
