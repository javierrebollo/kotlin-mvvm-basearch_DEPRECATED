package com.jrebollo.domain.usecase

import com.jrebollo.domain.Tracker
import com.jrebollo.domain.response.Response
import com.jrebollo.domain.response.TaskResult
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlin.coroutines.CoroutineContext

abstract class UseCase<T> : CoroutineScope {
    private val supervisorJob = SupervisorJob()
    private val mainDispatcher = Dispatchers.Main
    private val backgroundDispatcher = Dispatchers.Default
    private var requestValues: RequestValues? = null
    protected val resultChannel = Channel<TaskResult<T>>()
    var tracker: Tracker? = null

    private val receiverChannel: ReceiveChannel<TaskResult<T>> = resultChannel

    override val coroutineContext: CoroutineContext
        get() = supervisorJob + mainDispatcher

    protected abstract suspend fun run(requestValues: RequestValues)

    operator fun invoke(requestValues: RequestValues = sEmptyRequestValues, response: Response<T>) {
        launch {
            receiverChannel.consumeEach {
                response.invoke(it)
            }
        }
        launch {
            withContext(backgroundDispatcher) {
                run(requestValues)
            }
        }
    }

    protected fun <T> startAsync(block: suspend () -> T): Deferred<T> = async(supervisorJob) {
        block()
    }

    fun clear() {
        resultChannel.close()
        supervisorJob.cancel()
    }

    interface RequestValues
}

internal val sEmptyRequestValues = object : UseCase.RequestValues {}
