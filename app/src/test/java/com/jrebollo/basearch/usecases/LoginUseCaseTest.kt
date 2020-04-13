package com.jrebollo.basearch.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jrebollo.basearch.CoroutineTestRule
import com.jrebollo.domain.controller.UserRepository
import com.jrebollo.domain.response.Response
import com.jrebollo.domain.usecase.LoginUseCase
import io.mockk.every
import io.mockk.slot
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class LoginUseCaseTest {

    private lateinit var loginUseCase: LoginUseCase

    @Mock
    private lateinit var userRepository: UserRepository

    @Captor
    private lateinit var responseCaptor: ArgumentCaptor<Response<Boolean>>


    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutineTestRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        loginUseCase = LoginUseCase(userRepository)
    }

    @Test
    fun loginCorrectTest() {
        var token = "Fake_token"
        val response = slot<Response<Boolean>>()

        every {
            loginUseCase.invoke(any(), any()) {
                capture(response)
            }
        } answers {
            value
        }

        response.captured
    }
}
