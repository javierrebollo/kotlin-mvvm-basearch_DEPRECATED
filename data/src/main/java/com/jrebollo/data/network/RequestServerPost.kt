package com.jrebollo.data.network

import org.json.JSONObject

abstract class RequestServerPost<T> : BaseServerRequest<T> {
    override val type: BaseServerRequest.Type
        get() = BaseServerRequest.Type.POST

    abstract fun buildBody(): JSONObject
}