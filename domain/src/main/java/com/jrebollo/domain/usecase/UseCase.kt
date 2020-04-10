package com.jrebollo.domain.usecase

import com.jrebollo.domain.Tracker
import com.jrebollo.domain.response.ErrorResult
import com.jrebollo.domain.response.Response
import com.jrebollo.domain.response.SuccessResult
import com.jrebollo.domain.response.TaskResult
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.selects.select
import kotlin.coroutines.CoroutineContext

abstract class UseCase<V : UseCase.RequestValues, T> : CoroutineScope {
    protected val TAG: String = this::class.java.simpleName
    private val supervisorJob = SupervisorJob()
    private val mainDispatcher = Dispatchers.Main
    private val backgroundDispatcher = Dispatchers.Default
    private val ioDispatcher = Dispatchers.Default
    private var requestValues: RequestValues? = null
    protected val resultChannel = Channel<TaskResult<T>>()
    lateinit var tracker: Tracker

    private val receiverChannel: ReceiveChannel<TaskResult<T>> = resultChannel

    override val coroutineContext: CoroutineContext
        get() = supervisorJob + mainDispatcher

    protected abstract suspend fun run(requestValues: V)

    protected fun execute(requestValues: V, response: Response<T>) {
        launch {
            select<Unit> {
                receiverChannel.onReceive {
                    response.invoke(it)
                }
            }
            //ToDo Change select for consumeEach when consumeEach is not experimental
//            receiverChannel.consumeEach {
//                response.invoke(it)
//            }

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

    protected suspend fun <T> runIOBlock(block: suspend () -> T) = withContext(ioDispatcher) {
        block()
    }

    protected suspend fun sendSuccess(value: T) {
        resultChannel.send(SuccessResult(value))
    }

    protected suspend fun sendError(value: Exception) {
        resultChannel.send(ErrorResult(value))
    }

    fun clear() {
        resultChannel.close()
        supervisorJob.cancel()
    }

    interface RequestValues
}

internal val sEmptyRequestValues = object : UseCase.RequestValues {}
