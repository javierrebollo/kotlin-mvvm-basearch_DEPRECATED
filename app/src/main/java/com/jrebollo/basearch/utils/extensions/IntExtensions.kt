package com.jrebollo.basearch.utils.extensions

import androidx.annotation.StringRes
import com.jrebollo.basearch.MyApplication

fun @receiver:StringRes Int.getString(): String {
    return MyApplication.instance.getString(this)
}