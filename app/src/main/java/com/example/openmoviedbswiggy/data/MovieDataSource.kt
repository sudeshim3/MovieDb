package com.example.openmoviedbswiggy.data

import com.example.openmoviedbswiggy.datamodel.MovieDetailDataModel
import com.example.openmoviedbswiggy.datamodel.Result
import com.example.openmoviedbswiggy.datamodel.SearchResult

interface MovieDataSource {

    suspend fun fetchMovies(searchString: String, pageNumber: Int): Result<SearchResult>
    suspend fun fetchMovieDetails(movieId: String): Result<MovieDetailDataModel>
}
