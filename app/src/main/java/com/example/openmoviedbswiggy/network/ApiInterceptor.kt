package com.example.openmoviedbswiggy.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url

        val newUrl = originalHttpUrl.newBuilder()
            .addQueryParameter("apikey", apiKey)
            .build()

        val updateRequest = original.newBuilder().url(newUrl).build()
        return chain.proceed(updateRequest)
    }
}
