package com.example.openmoviedbswiggy.ui

import com.example.openmoviedbswiggy.data.MovieDataSource
import com.example.openmoviedbswiggy.datamodel.MovieDetailDataModel
import com.example.openmoviedbswiggy.datamodel.Result
import com.example.openmoviedbswiggy.datamodel.SearchResult

class FakeMovieDataSource: MovieDataSource {

    override suspend fun fetchMovies(searchString: String, pageNumber: Int): Result<SearchResult> {
    }

    override suspend fun fetchMovieDetails(movieId: String): Result<MovieDetailDataModel> {
    }

}
