package com.example.moviedb

import androidx.paging.PagingSource
import com.example.moviedb.datamodel.MovieDataModel
import com.example.moviedb.datamodel.MovieDetailDataModel
import com.example.moviedb.datamodel.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun fetchMovies(searchString: String): PagingSource<Int, MovieDataModel>
    fun getCurrentSearchResult(): Channel<MovieResultState>
    fun fetchMovieDetail(movieId: String): Flow<Result<MovieDetailDataModel>>
}
