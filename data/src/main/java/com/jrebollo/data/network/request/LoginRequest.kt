package com.jrebollo.data.network.request

import com.jrebollo.data.network.RequestServerPost
import com.jrebollo.domain.Tracker
import com.jrebollo.domain.entity.User
import okhttp3.HttpUrl

class LoginRequest : RequestServerPost<User>() {
    override val needAuthorization: Boolean
        get() = false
    override val httpUrl: HttpUrl
        get() = TODO("Not yet implemented")

    override fun parseBody(): String {
        TODO("Not yet implemented")
    }

    override fun parse(response: String, tracker: Tracker): User {
        TODO("Not yet implemented")
    }
}