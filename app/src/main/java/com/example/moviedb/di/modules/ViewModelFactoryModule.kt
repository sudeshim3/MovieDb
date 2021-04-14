package com.example.moviedb.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.moviedb.MovieVMFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(movieVMFactory: MovieVMFactory): ViewModelProvider.Factory
}
