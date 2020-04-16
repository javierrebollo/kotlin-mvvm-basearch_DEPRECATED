package com.jrebollo.basearch.exception

enum class ExceptionCode {
    WRONG_PASSWORD,
    DB_INSERT_FAIL
}

class MyAppException : Exception {

    val code: ExceptionCode

    constructor(code: ExceptionCode) {
        this.code = code
    }

    constructor(code: ExceptionCode, message: String) : super(message) {
        this.code = code
    }

    override fun toString(): String {
        val message = localizedMessage
        return if (message != null) {
            "MyAppException($code, $message)"
        } else {
            "MyAppException($code)"
        }
    }
}

val Exception?.safeLocalizedMessage: String
    get() = this?.localizedMessage ?: "(null)"
