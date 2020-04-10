package com.jrebollo.data.helper

import com.jrebollo.domain.helper.LiveDataHandler
import com.jrebollo.domain.helper.LiveDataHandlerDispatcher

class LiveDataHandlerDispatcherImpl : LiveDataHandlerDispatcher {
    override fun <T> provide(): LiveDataHandler<T> {
        return LiveDataHandlerImpl()
    }
}