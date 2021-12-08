package com.the.markers.base.model

import com.the.markers.base.error.ErrorEntity


sealed class DataResult<out T> {

    data class Success<out T>(val data: T) : DataResult<T>()

    data class Error(val error: ErrorEntity) : DataResult<Nothing>()
}