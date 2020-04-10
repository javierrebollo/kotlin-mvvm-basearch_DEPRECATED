package com.jrebollo.domain.helper

interface LiveDataHandlerDispatcher {
    fun <T> provide(): LiveDataHandler<T>
}