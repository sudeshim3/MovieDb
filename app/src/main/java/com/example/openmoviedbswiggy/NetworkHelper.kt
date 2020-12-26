package com.example.openmoviedbswiggy

import com.example.openmoviedbswiggy.datamodel.Result
import retrofit2.Response

suspend fun <T : Any> safeApiRequest(
    call: suspend () -> Response<T>,
    errorMessage: String
): Result<T> {
    try {
        val response = call()
        return when {
            response.isSuccessful && (response.body() is T) -> {
                Result.Success(response.body()!!)
            }
            (response.code() == 401) -> {
                Result.Error(Exception("$errorMessage ${response.errorBody()} ${response.message()}"))
            }
            else -> {
                Result.Error(Exception("$errorMessage ${response.errorBody()} ${response.message()}"))
            }
        }
    } catch (e: Exception) {
        return Result.Error(Exception(errorMessage, e))
    }
}
