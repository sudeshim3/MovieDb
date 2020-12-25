package com.example.openmoviedbswiggy

import com.example.openmoviedbswiggy.datamodel.BaseResponseDataModel
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

// http://www.omdbapi.com/?apikey={API_KEY}&s={SEARCH_STRING}&page={PAGE_NO}
interface OmbdApi {

    @GET("s={searchTerm}&page={page}")
    suspend fun getSearchResult(@Path("searchTerm") searchString: String, @Path("page") pageNo: Int): Response<BaseResponseDataModel<JSONObject>>

    @GET
    suspend fun getMovieDetails()
}
