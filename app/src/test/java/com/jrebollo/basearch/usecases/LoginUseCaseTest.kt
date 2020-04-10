package com.jrebollo.basearch.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jrebollo.basearch.CoroutineTestRule
import com.jrebollo.domain.controller.UserRepository
import com.jrebollo.domain.response.SuccessResult
import com.jrebollo.domain.response.on
import com.jrebollo.domain.usecase.LoginUseCase
import com.nhaarman.mockitokotlin2.any
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class LoginUseCaseTest {

    private lateinit var loginUseCase: LoginUseCase

    @Mock
    private lateinit var userRepository: UserRepository

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
        Mockito.`when`(userRepository.login(any(), any())).thenAnswer {
            return@thenAnswer SuccessResult(token)
        }
        loginUseCase(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()) {
            it.on(
                success = { result ->
                    Assert.assertTrue(false)
                    Assert.assertEquals(userRepository.token, "pepito")
                },
                failure = {
                    assert(false)
                }
            )
        }
    }
}
