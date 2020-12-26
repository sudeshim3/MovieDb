package com.example.openmoviedbswiggy.di

import com.example.openmoviedbswiggy.MovieDataSource
import com.example.openmoviedbswiggy.MovieDataSourceImpl
import com.example.openmoviedbswiggy.MovieRepository
import com.example.openmoviedbswiggy.MovieRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    abstract fun bindMovieDataSource(movieDataSourceImpl: MovieDataSourceImpl): MovieDataSource
}
