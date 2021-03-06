package com.example.openmoviedbswiggy.ui

import androidx.paging.PagingSource
import com.example.openmoviedbswiggy.data.MovieDataSource
import com.example.openmoviedbswiggy.data.MovieRepository
import com.example.openmoviedbswiggy.datamodel.MovieDataModel
import com.example.openmoviedbswiggy.datamodel.MovieDetailDataModel
import com.example.openmoviedbswiggy.datamodel.MovieResultState
import com.example.openmoviedbswiggy.datamodel.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepository(val fakeMovieDataSource: FakeMovieDataSource): MovieRepository {
    override fun fetchMovies(searchString: String): PagingSource<Int, MovieDataModel> {
        TODO("Not yet implemented")
    }

    override fun getCurrentSearchResult(): Channel<MovieResultState> {
        TODO("Not yet implemented")
    }

    override fun fetchMovieDetail(movieId: String): Flow<Result<MovieDetailDataModel>> {
        return flow {
            val result = fakeMovieDataSource.fetchMovieDetails("iron")
            emit(result)
        }
    }

}
