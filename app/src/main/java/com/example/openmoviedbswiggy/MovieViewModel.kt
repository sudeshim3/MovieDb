package com.example.openmoviedbswiggy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val movieRepositoryImpl: MovieRepository) :
    ViewModel() {

    fun fetchMovies() {
        viewModelScope.launch {
            movieRepositoryImpl.fetchMovies()
        }
    }
}
