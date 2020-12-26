package com.example.openmoviedbswiggy

import com.example.openmoviedbswiggy.datamodel.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val movieDataSourceImpl: MovieDataSource) : MovieRepository {

    override suspend fun fetchMovies(searchString: String) = withContext(Dispatchers.IO) {
        when (val result = movieDataSourceImpl.fetchMovies(searchString)) {
            is Result.Success -> {
            }
            is Result.Error -> {
                Timber.e(result.error.cause)
            }
        }
    }
}
