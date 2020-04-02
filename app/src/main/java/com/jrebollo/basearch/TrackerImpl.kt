package com.jrebollo.basearch

import android.util.Log
import com.jrebollo.domain.ErrorId
import com.jrebollo.domain.Tracker
import com.jrebollo.domain.exception.MyAppException

class TrackerImpl : Tracker {

    companion object {
        val instance: Tracker
            get() = MyApplication.tracker
    }

    override fun logError(errorId: ErrorId, message: String, exception: Throwable?, tag: String) {
        logError(errorId, message, exception, tag, false)
    }

    override fun logError(errorId: ErrorId, message: String, tag: String) {
        logError(errorId, message, exception = null, tag = tag)
    }

    override fun logWarning(errorId: ErrorId, message: String, exception: Throwable?, tag: String) {
        logError(errorId, message, exception, tag, true)
    }

    override fun logWarning(errorId: ErrorId, message: String, tag: String) {
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

    override fun logInfo(tag: String, message: String) {
        //ToDo -> Here send info to your tracker
    }

    override fun logDebug(tag: String, message: String) {
        Log.d(tag, message)
    }
}
