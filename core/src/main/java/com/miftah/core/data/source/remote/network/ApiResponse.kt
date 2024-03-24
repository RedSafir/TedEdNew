package com.miftah.core.data.source.remote.network

import androidx.annotation.Keep

@Keep
sealed class ApiResponse<out R> {
    data object Loading : ApiResponse<Nothing>()
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val errorMessage: String) : ApiResponse<Nothing>()
}