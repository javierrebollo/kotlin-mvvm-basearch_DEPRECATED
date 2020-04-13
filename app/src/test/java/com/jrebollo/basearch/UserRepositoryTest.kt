package com.jrebollo.basearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jrebollo.basearch.data.db.dao.UserDao
import com.jrebollo.basearch.data.helper.SharedPreferencesHelper
import com.jrebollo.basearch.data.network.ServerClient
import com.jrebollo.basearch.data.network.TaskResult
import com.jrebollo.basearch.data.repository.UserRepository
import com.jrebollo.basearch.exception.ExceptionCode
import com.jrebollo.basearch.exception.MyAppException
import com.jrebollo.basearch.utils.extensions.on
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserRepositoryTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var serverClient: ServerClient
    private lateinit var userDao: UserDao
    private lateinit var userRepository: UserRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        sharedPreferencesHelper = mockk<SharedPreferencesHelper>(relaxed = true)
        serverClient = mockk<ServerClient>()
        userDao = mockk<UserDao>()
        userRepository = UserRepository.getInstance(sharedPreferencesHelper, serverClient, userDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun loginSuccessTest() {
        var loadingCalled = false
        val token = "fake_token"
        val username = "my_username"
        val password = "my_password"

        every {
            serverClient.execute<String>(any(), any())
        } returns TaskResult.SuccessResult(token)

        runBlocking {
            userRepository.login(username, password)
                .on(
                    loading = {
                        loadingCalled = true
                    },
                    success = {
                        assertEquals(it, token)
                    },
                    failure = {
                        assert(false)
                    }
                )
        }

        assertTrue(loadingCalled)

        verify(exactly = 1) { sharedPreferencesHelper.token = token }

        confirmVerified(sharedPreferencesHelper)

    }

    @Test
    fun loginErrorTest() {
        var loadingCalled = false
        val token = "fake_token"
        val username = "my_username"
        val password = "my_password"
        val exception = MyAppException(ExceptionCode.WRONG_PASSWORD)

        every {
            serverClient.execute<String>(any(), any())
        } returns TaskResult.ErrorResult(exception)

        runBlocking {
            userRepository.login(username, password)
                .on(
                    loading = {
                        loadingCalled = true
                    },
                    success = {
                        assert(false)
                    },
                    failure = {
                        assertEquals(it, exception)
                    }
                )
        }

        assertTrue(loadingCalled)

        verify(exactly = 0) { sharedPreferencesHelper.token = token }

        confirmVerified(sharedPreferencesHelper)

    }
}