package com.jrebollo.domain.exception

enum class ExceptionCode {
    WRONG_PASSWORD
}

class MyAppException : Exception {

    val code: ExceptionCode

    constructor(code: ExceptionCode) {
        this.code = code
    }

    constructor(code: ExceptionCode, s: String) : super(s) {
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
