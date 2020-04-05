package com.jrebollo.basearch.data.network

import org.json.JSONObject

fun JSONObject?.parseToken(): String? {
    if (this == null) {
        return null
    }

    return this.getString("token")
}