package com.example.openmoviedbswiggy

import androidx.paging.PagingSource
import com.example.openmoviedbswiggy.datamodel.MovieDataModel
import com.example.openmoviedbswiggy.datamodel.Result
import com.example.openmoviedbswiggy.datamodel.SearchResult
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val movieDataSourceImpl: MovieDataSource) :
    MovieRepository {

    var searchKey = ""
    private var searchResult = Channel<Pair<Int, SearchResult>>(Channel.CONFLATED)

    override fun getCurrentSearchResult() = searchResult

    override fun fetchMovies(): PagingSource<Int, MovieDataModel> {
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
                            searchResult.send(Pair(nextPageNumber, result.data))
                            return LoadResult.Page(
                                data = result.data.searchResult,
                                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                                nextKey = nextPageNumber.plus(1)
                            )
                        }
                        is Result.Error -> {
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

    override fun setSearchString(searchString: String) {
        searchKey = searchString
    }
}
