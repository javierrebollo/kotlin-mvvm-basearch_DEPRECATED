package com.jrebollo.basearch.data.network.request

import com.jrebollo.basearch.data.network.RequestServerPost
import com.jrebollo.basearch.data.network.parseToken
import com.jrebollo.basearch.utils.Tracker
import org.json.JSONObject

class LoginRequest(
    private val userName: String,
    private val password: String
) : RequestServerPost<String>() {
    override val needAuthorization: Boolean
        get() = false
    override val path: String
        get() = "/login"

    override fun buildBody(): JSONObject {
        return JSONObject().apply {
            put("user", userName)
            put("password", password)
        }
    }

    override fun parse(response: String, tracker: Tracker): String {
        return JSONObject(response).parseToken()!!
    }
}