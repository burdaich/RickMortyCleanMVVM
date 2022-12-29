package com.example.domain.common

import okhttp3.ResponseBody

sealed class Resource<T>(val data: T? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(
        val isNetworkError: Boolean? = false,
        val errorCode: Int?,
        val unknownError: ResponseBody?,
        data: T? = null
    ) : Resource<T>(data)

    class Loading<T>(data: T? = null) : Resource<T>(data)
}