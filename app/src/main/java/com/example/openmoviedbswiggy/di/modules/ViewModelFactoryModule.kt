package com.example.openmoviedbswiggy.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.openmoviedbswiggy.di.MovieVMFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(movieVMFactory: MovieVMFactory): ViewModelProvider.Factory
}
