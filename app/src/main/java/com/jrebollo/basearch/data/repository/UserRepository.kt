package com.jrebollo.basearch.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.jrebollo.basearch.data.db.dao.UserDao
import com.jrebollo.basearch.data.db.entity.UserRoom
import com.jrebollo.basearch.data.entity.User
import com.jrebollo.basearch.data.helper.SharedPreferencesHelper
import com.jrebollo.basearch.data.network.ServerClient
import com.jrebollo.basearch.data.network.TaskResult
import com.jrebollo.basearch.data.network.on
import com.jrebollo.basearch.data.network.request.LoginRequest
import com.jrebollo.basearch.exception.ExceptionCode
import com.jrebollo.basearch.exception.MyAppException
import com.jrebollo.basearch.utils.extensions.failure
import com.jrebollo.basearch.utils.extensions.success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext
import java.util.*

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

    suspend fun loadNewUser() = channelFlow<TaskResult<User>> {
        send(TaskResult.Loading())
        withContext(Dispatchers.IO) {
            val newUser = generateFakeUser()
            val rowId = userDao.insert(UserRoom.from(newUser))

            if (rowId >= 0) {
                success(newUser)
            } else {
                failure(
                    MyAppException(
                        code = ExceptionCode.DB_INSERT_FAIL,
                        message = "Error when try to save user"
                    )
                )
            }

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

    private fun generateFakeUser(): User {
        val names: List<String> = listOf(
            "Pepe",
            "Manuel",
            "Antonio",
            "Javier",
            "Mario",
            "Isaac",
            "Fernando",
            "Jose"
        )
        val lastnames: List<String> = listOf(
            "Martin",
            "Rodrigez",
            "Perez",
            "Cinta",
            "Garcia",
            "Rebollo",
            "Prieto",
            "Diaz"
        )
        val email: List<String> = listOf(
            "emai1@gmail.com",
            "emai2@gmail.com",
            "emai3@gmail.com",
            "emai4@gmail.com",
            "emai5@gmail.com",
            "emai6@gmail.com",
            "emai7@gmail.com"
        )
        val gender: List<String> = listOf(
            "Male",
            "Female"
        )

        return User(
            id = UUID.randomUUID().toString(),
            name = names.random(),
            lastName = lastnames.random(),
            email = email.random(),
            gender = gender.random()
        )
    }


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