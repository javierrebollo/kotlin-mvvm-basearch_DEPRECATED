package com.jrebollo.data.network

abstract class RequestServerPost<T> : BaseServerRequest<T> {
    override val type: BaseServerRequest.Type
        get() = BaseServerRequest.Type.POST

    abstract fun parseBody(): String
}