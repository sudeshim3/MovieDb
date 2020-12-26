package com.example.openmoviedbswiggy

interface MovieRepository {
    suspend fun fetchMovies(searchString: String)
}
