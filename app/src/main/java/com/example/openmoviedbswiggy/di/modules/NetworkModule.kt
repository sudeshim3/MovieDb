package com.example.openmoviedbswiggy.di.modules

import AppConstant.BASE_URL
import android.os.Looper
import com.example.openmoviedbswiggy.ApiInterceptor
import com.example.openmoviedbswiggy.BuildConfig
import com.example.openmoviedbswiggy.OmbdApi
import com.example.openmoviedbswiggy.extensions.delegatingCallFactory
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
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw IllegalStateException("HTTP client initialized on main thread.")
        }
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
