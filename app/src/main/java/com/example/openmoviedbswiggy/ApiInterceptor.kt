package com.example.openmoviedbswiggy

import AppConstant.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor(private val apiToken: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url

        val newUrl = originalHttpUrl.newBuilder()
            .addQueryParameter("apikey", API_KEY)
            .build()

        val updateRequest = original.newBuilder().url(newUrl).build()
        return chain.proceed(updateRequest)
    }
}
