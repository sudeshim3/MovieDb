package com.example.openmoviedbswiggy

import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor(private val apiToken: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder().header("api_key", apiToken).build()
        return chain.proceed(request)
    }
}
