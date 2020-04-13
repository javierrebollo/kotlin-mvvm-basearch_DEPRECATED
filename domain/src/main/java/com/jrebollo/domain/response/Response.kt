package com.jrebollo.domain.response

//typealias Response<T> = (TaskResult<T>) -> Unit

sealed class TaskResult<T> {
    class Loading<T> : TaskResult<T>()
    data class ErrorResult<T>(val exception: Exception) : TaskResult<T>()
    data class SuccessResult<T>(val value: T) : TaskResult<T>()
}

inline fun <T, R> TaskResult<T>.on(loading: () -> R, success: (T) -> R, failure: (Exception) -> R): R {
    return when (this) {
        is TaskResult.Loading -> loading()
        is TaskResult.SuccessResult -> success(this.value)
        is TaskResult.ErrorResult -> failure(this.exception)
    }
}

sealed class RepositoryResult<T> {
    data class ErrorResult<T>(val exception: Exception) : RepositoryResult<T>()
    data class SuccessResult<T>(val value: T) : RepositoryResult<T>()
}

inline fun <T, R> RepositoryResult<T>.on(success: (T) -> R, failure: (Exception) -> R): R {
    return when (this) {
        is RepositoryResult.SuccessResult -> success(this.value)
        is RepositoryResult.ErrorResult -> failure(this.exception)
    }
}

