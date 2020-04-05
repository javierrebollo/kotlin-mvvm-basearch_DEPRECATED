package com.jrebollo.basearch.data.network

import com.jrebollo.basearch.BuildConfig
import com.jrebollo.basearch.utils.Tracker
import okhttp3.HttpUrl

interface BaseServerRequest<T> {

    val type: Type
    val needAuthorization: Boolean
    val path: String
    val httpUrl: HttpUrl
        get() = HttpUrl.get("${BuildConfig.BASE_URL}${path}")

    @Throws(Exception::class)
    fun parse(response: String, tracker: Tracker): T

    enum class Type {
        POST, GET
    }
}