package com.example.moviedb

import com.example.moviedb.datamodel.MovieDetailDataModel
import com.example.moviedb.datamodel.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// http://www.omdbapi.com/?apikey={API_KEY}&s={SEARCH_STRING}&page={PAGE_NO}
interface OmbdApi {

    @GET("/")
    suspend fun getSearchResult(
        @Query("s") searchString: String,
        @Query("page") pageNo: Int
    ): Response<SearchResult>

    @GET("/")
    suspend fun getMovieDetails(
        @Query("i") movieId: String,
        @Query("plot") plotLength: String = "full"
    ): Response<MovieDetailDataModel>
}
