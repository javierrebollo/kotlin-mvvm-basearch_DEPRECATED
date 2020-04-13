package com.jrebollo.basearch.data.network

sealed class TaskResult<T> {
    class Loading<T> : TaskResult<T>()
    data class ErrorResult<T>(val exception: Exception) : TaskResult<T>()
    data class SuccessResult<T>(val value: T) : TaskResult<T>()
}

inline fun <T, R> TaskResult<T>.on(
    noinline loading: (() -> R)? = null,
    success: (T) -> R,
    failure: (Exception) -> R
): R? {
    return when (this) {
        is TaskResult.Loading -> loading?.invoke()
        is TaskResult.SuccessResult -> success(this.value)
        is TaskResult.ErrorResult -> failure(this.exception)
    }
}