package com.example.openmoviedbswiggy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val movieRepositoryImpl: MovieRepository) :
    ViewModel() {

    private val _movieListLiveData: MutableLiveData<MovieResponse> = MutableLiveData()
    val movieListLiveData = _movieListLiveData as LiveData<MovieResponse>
    fun fetchMovies(searchString: String) {
        _movieListLiveData.postValue(MovieResponse.Loading)
        viewModelScope.launch {
            val result = movieRepositoryImpl.fetchMovies(searchString)
            _movieListLiveData.postValue(result)
        }
    }
}
