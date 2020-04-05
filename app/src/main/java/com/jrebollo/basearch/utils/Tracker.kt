package com.jrebollo.basearch.utils

import android.util.Log
import com.jrebollo.basearch.exception.MyAppException

object Tracker {
    val SERVER_REQUEST_ERROR = ErrorId("Server request error")
    val MISSING_DATA = ErrorId("Missing data")

    fun logError(errorId: ErrorId, message: String, exception: Throwable?, tag: String) {
        logError(errorId, message, exception, tag, false)
    }

    fun logError(errorId: ErrorId, message: String, tag: String) {
        logError(errorId, message, exception = null, tag = tag)
    }

    fun logWarning(errorId: ErrorId, message: String, exception: Throwable?, tag: String) {
        logError(errorId, message, exception, tag, true)
    }

    fun logWarning(errorId: ErrorId, message: String, tag: String) {
        logWarning(errorId, message, exception = null, tag = tag)
    }

    private fun logError(errorId: ErrorId, message: String, exception: Throwable?, tag: String, warning: Boolean) {
        exception?.printStackTrace()

        val msg = "${errorId.value} - $message" +
            when {
                exception is MyAppException -> "\n$exception"
                exception != null -> "\n${exception.javaClass.simpleName}: ${exception.message}"
                else -> ""
            }

        if (warning) {
            //ToDo -> Here send warning to your tracker
            Log.w(tag, msg)
        } else {
            //ToDo -> Here send error to your tracker
            Log.e(tag, msg)
        }
    }

    fun logInfo(tag: String, message: String) {
        //ToDo -> Here send info to your tracker
    }

    fun logDebug(tag: String, message: String) {
        Log.d(tag, message)
    }
}

inline class ErrorId(val value: String)
