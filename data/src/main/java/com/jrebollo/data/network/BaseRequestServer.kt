package com.jrebollo.data.network

import com.jrebollo.domain.Tracker
import okhttp3.HttpUrl

interface BaseServerRequest<T> {

    val type: Type
    val needAuthorization: Boolean
    val httpUrl: HttpUrl

    @Throws(Exception::class)
    fun parse(response: String, tracker: Tracker): T

    enum class Type {
        POST, GET
    }
}