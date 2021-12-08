package com.the.markers.base.error

import com.the.markers.base.error.ErrorEntity


interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
}