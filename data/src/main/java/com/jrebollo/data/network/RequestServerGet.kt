package com.jrebollo.data.network

abstract class RequestServerGet<T> : BaseServerRequest<T> {
    override val type: BaseServerRequest.Type
        get() = BaseServerRequest.Type.GET
}