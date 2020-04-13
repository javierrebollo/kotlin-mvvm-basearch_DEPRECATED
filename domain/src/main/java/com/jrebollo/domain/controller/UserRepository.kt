package com.jrebollo.domain.controller

import com.jrebollo.domain.entity.User
import com.jrebollo.domain.helper.LiveDataHandler
import com.jrebollo.domain.response.RepositoryResult

interface UserRepository {
    var token: String?
    fun login(username: String, password: String): RepositoryResult<String>
    val liveUsers: LiveDataHandler<List<User>>
    suspend fun addUser(user: User): RepositoryResult<Boolean>
}