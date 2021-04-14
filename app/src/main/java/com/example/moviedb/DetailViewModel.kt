package com.example.moviedb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.datamodel.MovieDetailDataModel
import com.example.moviedb.datamodel.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val movieRepositoryImpl: MovieRepository) :
    ViewModel() {
    private val liveDataModel: MutableLiveData<Result<MovieDetailDataModel>> = MutableLiveData()
    val liveData: LiveData<Result<MovieDetailDataModel>> = liveDataModel
    var movieId = ""
    fun fetchMovieDetail(movieId: String) {
        this.movieId = movieId
        viewModelScope.launch {
            CoroutineScope(coroutineContext).launch {
                movieRepositoryImpl.fetchMovieDetail(movieId).collectLatest {
                    liveDataModel.postValue(it)
                }
            }
        }
    }
}
