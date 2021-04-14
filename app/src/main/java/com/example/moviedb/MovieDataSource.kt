package com.example.moviedb

import com.example.moviedb.datamodel.MovieDetailDataModel
import com.example.moviedb.datamodel.Result
import com.example.moviedb.datamodel.SearchResult

interface MovieDataSource {

    suspend fun fetchMovies(searchString: String, pageNumber: Int): Result<SearchResult>
    suspend fun fetchMovieDetails(movieId: String): Result<MovieDetailDataModel>
}
