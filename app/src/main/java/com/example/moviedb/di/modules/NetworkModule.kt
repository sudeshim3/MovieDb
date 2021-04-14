package com.example.moviedb.di.modules

import AppConstant.BASE_URL
import com.example.moviedb.ApiInterceptor
import com.example.moviedb.BuildConfig
import com.example.moviedb.OmbdApi
import com.example.moviedb.extensions.delegatingCallFactory
import dagger.Lazy
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: Lazy<OkHttpClient>,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .delegatingCallFactory(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(apiInterceptor: ApiInterceptor): OkHttpClient {
        val REQUEST_TIMEOUT = 5L

        return OkHttpClient.Builder().apply {
            connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            addInterceptor(apiInterceptor)
            addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
        }.build()
    }

    @Provides
    @Singleton
    fun provideGsonFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideApiInterceptor(): ApiInterceptor {
        return ApiInterceptor(BuildConfig.API_KEY)
    }

    @Provides
    @Singleton
    fun getApiInterface(retrofit: Retrofit): OmbdApi {
        return retrofit.create(OmbdApi::class.java)
    }
}
