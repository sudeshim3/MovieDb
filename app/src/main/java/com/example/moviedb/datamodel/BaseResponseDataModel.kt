package com.example.moviedb.datamodel

import com.google.gson.annotations.SerializedName

data class BaseResponseDataModel<T>(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val data: T,
    @SerializedName("error")
    val error: String? = null
)
