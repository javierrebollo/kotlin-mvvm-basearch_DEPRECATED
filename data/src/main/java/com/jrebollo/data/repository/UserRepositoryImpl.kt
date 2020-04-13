package com.jrebollo.data.repository

import android.util.Log
import androidx.lifecycle.Transformations
import com.jrebollo.data.db.dao.UserDao
import com.jrebollo.data.db.entity.UserRoom
import com.jrebollo.data.helper.LiveDataHandlerImpl
import com.jrebollo.data.network.ServerClient
import com.jrebollo.data.network.request.LoginRequest
import com.jrebollo.domain.controller.UserRepository
import com.jrebollo.domain.entity.User
import com.jrebollo.domain.helper.LiveDataHandler
import com.jrebollo.domain.helper.SharedPreferencesHelper
import com.jrebollo.domain.response.RepositoryResult

class UserRepositoryImpl private constructor(
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    private val serverClient: ServerClient,
    private val userDao: UserDao
) : UserRepository {
    private val TAG: String = this::class.java.simpleName

    override var token: String?
        get() = sharedPreferencesHelper.token
        set(value) {
            sharedPreferencesHelper.token = value
        }

    override fun login(username: String, password: String): RepositoryResult<String> {
        return serverClient.execute(
            LoginRequest(username, password)
        )
    }

    override val liveUsers: LiveDataHandler<List<User>> = LiveDataHandlerImpl(
        Transformations.map(userDao.getAllUsers()) {
            it.map { userRoom ->
                userRoom.toUser()
            }
        }
    )

    override suspend fun addUser(user: User): RepositoryResult<Boolean> {
        val returned = userDao.insert(UserRoom.from(user))
        Log.d(TAG, "Returned value $returned")
        return RepositoryResult.SuccessResult(true)
    }

    companion object {

        @Volatile private var instance: UserRepository? = null

        fun getInstance(
            sharedPreferencesHelper: SharedPreferencesHelper,
            serverClient: ServerClient,
            userDao: UserDao
        ) =
            instance ?: synchronized(this) {
                instance ?: UserRepositoryImpl(
                    sharedPreferencesHelper,
                    serverClient,
                    userDao
                ).also { instance = it }
            }
    }
}