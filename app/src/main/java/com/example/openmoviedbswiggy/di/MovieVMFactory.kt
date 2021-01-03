package com.example.openmoviedbswiggy.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.openmoviedbswiggy.data.MovieRepository
import com.example.openmoviedbswiggy.ui.viewmodel.MovieViewModel
import javax.inject.Inject

class MovieVMFactory @Inject constructor(private val movieRepository: MovieRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(movieRepository) as T
    }
}
