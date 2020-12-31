package com.example.openmoviedbswiggy

import androidx.paging.PagingSource
import com.example.openmoviedbswiggy.datamodel.MovieDataModel
import com.example.openmoviedbswiggy.datamodel.MovieDetailDataModel
import com.example.openmoviedbswiggy.datamodel.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun fetchMovies(): PagingSource<Int, MovieDataModel>
    fun setSearchString(searchString: String)
    fun getCurrentSearchResult(): Channel<MovieResultState>
    fun fetchMovieDetail(movieId: String): Flow<Result<MovieDetailDataModel>>
}
