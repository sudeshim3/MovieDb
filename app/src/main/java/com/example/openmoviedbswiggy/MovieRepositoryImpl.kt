package com.example.openmoviedbswiggy

import com.example.openmoviedbswiggy.datamodel.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val movieDataSourceImpl: MovieDataSource) : MovieRepository {

    override suspend fun fetchMovies(searchString: String) = withContext(Dispatchers.IO) {
        when (val result = movieDataSourceImpl.fetchMovies(searchString)) {
            is Result.Success -> {
                if (result.data.response) {
                    MovieResponse.Data(result.data)
                } else {
                    MovieResponse.Error(result.data.error)
                }
            }
            is Result.Error -> {
                val error = result.error.cause?.toString() ?: "Something went wrong"
                MovieResponse.Error(error)
            }
        }
    }
}
