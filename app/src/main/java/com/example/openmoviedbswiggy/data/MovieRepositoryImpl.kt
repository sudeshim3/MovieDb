package com.example.openmoviedbswiggy.data

import androidx.paging.PagingSource
import com.example.openmoviedbswiggy.datamodel.MovieDataModel
import com.example.openmoviedbswiggy.datamodel.MovieResultState
import com.example.openmoviedbswiggy.datamodel.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val movieDataSourceImpl: MovieDataSource) :
    MovieRepository {

    private var searchResult = Channel<MovieResultState>(Channel.CONFLATED)

    override fun getCurrentSearchResult() = searchResult

    override fun fetchMovies(searchKey: String): PagingSource<Int, MovieDataModel> {
        val pagingSource = object : PagingSource<Int, MovieDataModel>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDataModel> {
                try {
                    val nextPageNumber = params.key ?: 1
                    when (
                        val result =
                            movieDataSourceImpl.fetchMovies(
                                searchKey, nextPageNumber
                            )
                    ) {
                        is Result.Success -> {
                            searchResult.send(
                                MovieResultState.PageResult(
                                    nextPageNumber,
                                    result.data
                                )
                            )
                            val searchResult = result.data.searchResult
                            return LoadResult.Page(
                                data = searchResult ?: listOf(),
                                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                                nextKey = if (searchResult == null) null else nextPageNumber.plus(
                                    1
                                )
                            )
                        }
                        is Result.Error -> {
                            searchResult.send(MovieResultState.PageErrorResponse(result.error))
                            return LoadResult.Error(result.error)
                        }
                    }
                } catch (e: Exception) {
                    return LoadResult.Error(e)
                }
            }
        }
        return pagingSource
    }

    override fun fetchMovieDetail(movieId: String) = flow {
        val result = movieDataSourceImpl.fetchMovieDetails(movieId)
        emit(result)
    }
}
