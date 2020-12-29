package com.example.openmoviedbswiggy

import androidx.paging.PagingSource
import com.example.openmoviedbswiggy.datamodel.MovieDataModel
import com.example.openmoviedbswiggy.datamodel.SearchResult
import kotlinx.coroutines.channels.Channel

interface MovieRepository {
    fun fetchMovies(): PagingSource<Int, MovieDataModel>
    fun setSearchString(searchString: String)
    fun getCurrentSearchResult(): Channel<Pair<Int, SearchResult>>
}
