package com.example.openmoviedbswiggy

import com.example.openmoviedbswiggy.datamodel.MovieDetailDataModel
import com.example.openmoviedbswiggy.datamodel.Result
import com.example.openmoviedbswiggy.datamodel.SearchResult
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(private val ombdApi: OmbdApi) : MovieDataSource {

    override suspend fun fetchMovies(searchString: String, pageNumber: Int): Result<SearchResult> =
        safeApiRequest(
            call = { ombdApi.getSearchResult(searchString, pageNumber) },
            errorMessage = "Something went wrong, please try again!"
        )

    override suspend fun fetchMovieDetails(movieId: String): Result<MovieDetailDataModel> =
        safeApiRequest(
            call = { ombdApi.getMovieDetails(movieId) },
            errorMessage = "Something went wrong, please try again"
        )
}
