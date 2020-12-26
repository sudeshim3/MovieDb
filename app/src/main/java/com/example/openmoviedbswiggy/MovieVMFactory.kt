package com.example.openmoviedbswiggy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MovieVMFactory(private val movieRepository: MovieRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(movieRepository) as T
    }
}
