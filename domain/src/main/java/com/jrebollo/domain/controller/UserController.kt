package com.jrebollo.domain.controller

import com.jrebollo.domain.response.TaskResult

interface UserController {
    var token: String?
    fun login(username: String, password: String): TaskResult<String>
}