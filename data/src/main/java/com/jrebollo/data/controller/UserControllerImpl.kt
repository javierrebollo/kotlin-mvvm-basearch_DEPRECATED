package com.jrebollo.data.controller

import com.jrebollo.data.network.ServerClient
import com.jrebollo.data.network.request.LoginRequest
import com.jrebollo.domain.controller.UserController
import com.jrebollo.domain.helper.SharedPreferencesHelper
import com.jrebollo.domain.response.TaskResult

class UserControllerImpl(
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    private val serverClient: ServerClient
) : UserController {

    override var token: String?
        get() = sharedPreferencesHelper.token
        set(value) {
            sharedPreferencesHelper.token = value
        }

    override fun login(username: String, password: String): TaskResult<String> {
        return serverClient.execute(
            LoginRequest(username, password)
        )
    }
}