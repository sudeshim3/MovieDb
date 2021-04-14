package com.example.moviedb

import AppConstant.MIN_CHAR_FOR_SEARCH
import AppConstant.PAGE_SIZE
import AppConstant.SEARCH_DEBOUNCE_INTERVAL
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviedb.datamodel.MovieDataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MovieViewModel @Inject constructor(private val movieRepositoryImpl: MovieRepository) :
    ViewModel() {
    var searchString: String = ""
    var searchDebounce: ((String) -> Unit)
    private val _searchResult: MutableLiveData<PagingData<MovieDataModel>> = MutableLiveData()
    val pagedSearchResult: LiveData<PagingData<MovieDataModel>> = _searchResult

    init {
        searchDebounce = debounce(
            SEARCH_DEBOUNCE_INTERVAL,
            viewModelScope.coroutineContext
        ) { searchString ->
            if (searchString.length > MIN_CHAR_FOR_SEARCH) {
                updateSearchKey(searchString)
                collectLatestSearchResult()
            }
        }
    }

    private fun pagerSource() = Pager(
        PagingConfig(
            pageSize = PAGE_SIZE,
            initialLoadSize = PAGE_SIZE * 2
        )
    ) {
        movieRepositoryImpl.fetchMovies(searchString)
    }.flow

    fun collectLatestSearchResult() {
        CoroutineScope(viewModelScope.coroutineContext).launch {
            pagerSource().collectLatest {
                _searchResult.postValue(it)
            }
        }
    }

    private fun updateSearchKey(searchString: String) {
        this.searchString = searchString
    }

    fun observeSearchResult(): LiveData<MovieResultState> {
        return movieRepositoryImpl.getCurrentSearchResult().receiveAsFlow().asLiveData()
    }

    private fun debounce(
        delayMs: Long,
        coroutineContext: CoroutineContext,
        f: (String) -> Unit
    ): (String) -> Unit {
        var debounceJob: Job? = null
        return {
            debounceJob?.cancel()
            debounceJob = CoroutineScope(coroutineContext).launch {
                delay(delayMs)
                f(it)
            }
        }
    }
}
