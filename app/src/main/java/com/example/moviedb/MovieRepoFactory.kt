package com.example.moviedb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MovieRepoFactory(private val movieRepositoryImpl: MovieRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(movieRepositoryImpl) as T
    }
}
