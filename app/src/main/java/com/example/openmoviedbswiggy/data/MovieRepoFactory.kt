package com.example.openmoviedbswiggy.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.openmoviedbswiggy.ui.viewmodel.MovieViewModel

class MovieRepoFactory(private val movieRepositoryImpl: MovieRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(movieRepositoryImpl) as T
    }
}
