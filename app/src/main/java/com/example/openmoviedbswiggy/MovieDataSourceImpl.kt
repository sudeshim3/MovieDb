package com.example.openmoviedbswiggy

import com.example.openmoviedbswiggy.datamodel.Result
import com.example.openmoviedbswiggy.datamodel.SearchResult
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(val ombdApi: OmbdApi) : MovieDataSource {

    override suspend fun fetchMovies(): Result<SearchResult> = safeApiRequest(
        call = { ombdApi.getSearchResult("rock", 1) },
        errorMessage = "Something went wrong, please try again!"
    )
}
