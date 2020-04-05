package com.jrebollo.basearch.data.network

abstract class RequestServerGet<T> : BaseServerRequest<T> {
    override val type: BaseServerRequest.Type
        get() = BaseServerRequest.Type.GET
}