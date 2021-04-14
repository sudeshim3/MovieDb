package com.example.moviedb.di

import com.example.moviedb.MovieDataSource
import com.example.moviedb.MovieDataSourceImpl
import com.example.moviedb.MovieRepository
import com.example.moviedb.MovieRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    abstract fun bindMovieDataSource(movieDataSourceImpl: MovieDataSourceImpl): MovieDataSource
}
