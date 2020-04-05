package com.jrebollo.basearch.data.network

typealias NetworkResponse<T> = (TaskResult<T>) -> Unit

sealed class TaskResult<T>

data class ErrorResult<T>(val exception: Exception) : TaskResult<T>()

data class SuccessResult<T>(val value: T) : TaskResult<T>()

inline fun <T, R> TaskResult<T>.on(success: (T) -> R, failure: (Exception) -> R): R {
    return when (this) {
        is SuccessResult -> success(this.value)
        is ErrorResult -> failure(this.exception)
    }
}