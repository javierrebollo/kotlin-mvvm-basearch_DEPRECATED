package com.jrebollo.domain.usecase

import com.jrebollo.domain.Tracker
import com.jrebollo.domain.helper.LiveDataHandler

abstract class LiveDataUseCase<V : UseCase.RequestValues, T> {
    protected val TAG: String = this::class.java.simpleName
    private var requestValues: RequestValues? = null
    lateinit var tracker: Tracker

    protected abstract fun run(requestValues: V): LiveDataHandler<T>

    protected fun execute(requestValues: V): LiveDataHandler<T> {
        return run(requestValues)
    }

    interface RequestValues
}