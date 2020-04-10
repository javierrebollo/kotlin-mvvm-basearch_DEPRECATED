package com.jrebollo.domain.helper

interface LiveDataHandler<T> {
    fun postValue(value: T)
}