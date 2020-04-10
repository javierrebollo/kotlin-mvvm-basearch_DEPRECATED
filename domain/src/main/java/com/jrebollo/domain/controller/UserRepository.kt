package com.jrebollo.domain.controller

import com.jrebollo.domain.entity.User
import com.jrebollo.domain.helper.LiveDataHandler
import com.jrebollo.domain.response.TaskResult

interface UserRepository {
    var token: String?
    fun login(username: String, password: String): TaskResult<String>
    val liveUsers: LiveDataHandler<List<User>>
    suspend fun addUser(user: User): TaskResult<Boolean>
}