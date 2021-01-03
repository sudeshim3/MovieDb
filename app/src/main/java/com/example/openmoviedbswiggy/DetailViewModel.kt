package com.example.openmoviedbswiggy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openmoviedbswiggy.datamodel.MovieDetailDataModel
import com.example.openmoviedbswiggy.datamodel.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val movieRepositoryImpl: MovieRepository) :
    ViewModel() {
    private val liveDataModel: MutableLiveData<Result<MovieDetailDataModel>> = MutableLiveData()
    var movieId = ""
    fun fetchMovieDetail(movieId: String): LiveData<Result<MovieDetailDataModel>> {
        this.movieId = movieId
        viewModelScope.launch {
            CoroutineScope(coroutineContext).launch {
                movieRepositoryImpl.fetchMovieDetail(movieId).collectLatest {
                    liveDataModel.postValue(it)
                }
            }
        }
        return liveDataModel
    }
}
