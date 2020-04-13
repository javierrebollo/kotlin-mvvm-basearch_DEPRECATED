package com.jrebollo.basearch.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.jrebollo.basearch.data.db.dao.UserDao
import com.jrebollo.basearch.data.entity.User
import com.jrebollo.basearch.data.helper.SharedPreferencesHelper
import com.jrebollo.basearch.data.network.ServerClient
import com.jrebollo.basearch.data.network.TaskResult
import com.jrebollo.basearch.data.network.on
import com.jrebollo.basearch.data.network.request.LoginRequest
import com.jrebollo.basearch.utils.extensions.failure
import com.jrebollo.basearch.utils.extensions.success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext

class UserRepository private constructor(
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    private val serverClient: ServerClient,
    private val userDao: UserDao
) {

    var token: String?
        get() = sharedPreferencesHelper.token
        set(value) {
            sharedPreferencesHelper.token = value
        }

    suspend fun login(username: String, password: String) = channelFlow<TaskResult<String>> {
        send(TaskResult.Loading())
        withContext(Dispatchers.IO) {
            delay(2_000)
            serverClient.execute(
                LoginRequest(username, password)
            ).on(
                success = {
                    token = it
                    success(it)
                },
                failure = {
                    failure(it)
                }
            )
        }
    }

    val liveUsers: LiveData<List<User>>
        get() =
            Transformations.map(userDao.getAllUsers()) {
                it.map { userRoom ->
                    userRoom.toUser()
                }
            }

    val isLogged: Boolean
        get() = !token.isNullOrEmpty()


    companion object {

        @Volatile private var instance: UserRepository? = null

        fun getInstance(
            sharedPreferencesHelper: SharedPreferencesHelper,
            serverClient: ServerClient,
            userDao: UserDao
        ) =
            instance ?: synchronized(this) {
                instance ?: UserRepository(
                    sharedPreferencesHelper,
                    serverClient,
                    userDao
                ).also { instance = it }
            }
    }
}