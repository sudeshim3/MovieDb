package com.example.openmoviedbswiggy.extensions

import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit

/**
 * Credits : https://github.com/ZacSweers/CatchUp/blob/9f80c4c4f6e25e171979a1f2cc67e2bef8f2c738/libraries/retrofitconverters/src/main/kotlin/io/sweers/catchup/libraries/retrofitconverters/RetrofitExt.kt#L31-L35
 */

@PublishedApi
internal inline fun Retrofit.Builder.callFactory(
    crossinline body: (Request) -> Call
) = callFactory(object : Call.Factory {
    override fun newCall(request: Request): Call = body(request)
})

@Suppress("NOTHING_TO_INLINE")
inline fun Retrofit.Builder.delegatingCallFactory(
    delegate: dagger.Lazy<OkHttpClient>
): Retrofit.Builder = callFactory {
    delegate.get().newCall(it)
}
