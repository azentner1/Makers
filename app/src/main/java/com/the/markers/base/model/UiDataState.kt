package com.the.markers.base.model

import com.the.markers.base.error.ErrorEntity


sealed class UiDataState<out T> {

    object Idle: UiDataState<Nothing>()

    data class Success<out T>(val data: T) : UiDataState<T>()

    data class Error(val error: ErrorEntity) : UiDataState<Nothing>()

    object Loading : UiDataState<Nothing>()
}
