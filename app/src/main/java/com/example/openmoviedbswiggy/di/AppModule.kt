package com.example.openmoviedbswiggy.di

import com.example.openmoviedbswiggy.data.MovieDataSource
import com.example.openmoviedbswiggy.data.MovieDataSourceImpl
import com.example.openmoviedbswiggy.data.MovieRepository
import com.example.openmoviedbswiggy.data.MovieRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    abstract fun bindMovieDataSource(movieDataSourceImpl: MovieDataSourceImpl): MovieDataSource
}
