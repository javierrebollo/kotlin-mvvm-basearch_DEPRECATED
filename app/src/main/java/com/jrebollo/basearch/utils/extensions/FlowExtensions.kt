package com.jrebollo.basearch.utils.extensions

import com.jrebollo.basearch.data.network.TaskResult
import com.jrebollo.basearch.data.network.on
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

suspend fun <V> ProducerScope<TaskResult<V>>.loading() {
    withContext(Dispatchers.Main) {
        send(TaskResult.Loading())
    }
}

suspend fun <V> ProducerScope<TaskResult<V>>.success(value: V) {
    withContext(Dispatchers.Main) {
        send(TaskResult.SuccessResult(value))
        close()
    }
}

suspend fun <V> ProducerScope<TaskResult<V>>.failure(exception: Exception) {
    withContext(Dispatchers.Main) {
        send(TaskResult.ErrorResult(exception))
        close()
    }
}

suspend fun <X, T : TaskResult<X>, R> Flow<T>.on(
    loading: (() -> R)? = null,
    success: (X) -> R,
    failure: (Exception) -> R
) {
    this.collect { value ->
        value.on(
            loading = {
                loading?.invoke()
            },
            success = {
                success.invoke(it)
            },
            failure = {
                failure.invoke(it)
            }
        )
    }
}