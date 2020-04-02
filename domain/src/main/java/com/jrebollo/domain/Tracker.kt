package com.jrebollo.domain

interface Tracker {
    companion object {
        val SERVER_REQUEST_ERROR = ErrorId("Server request error")
        val MISSING_DATA = ErrorId("Missing data")
    }

    fun logError(errorId: ErrorId, message: String, exception: Throwable?, tag: String)

    fun logError(errorId: ErrorId, message: String, tag: String)

    fun logWarning(errorId: ErrorId, message: String, exception: Throwable?, tag: String)

    fun logWarning(errorId: ErrorId, message: String, tag: String)

    fun logInfo(tag: String, message: String)

    fun logDebug(tag: String, message: String)
}

inline class ErrorId(val value: String)
