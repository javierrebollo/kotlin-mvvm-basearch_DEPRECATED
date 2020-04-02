package com.jrebollo.domain.usecase

import com.jrebollo.domain.controller.UserController
import com.jrebollo.domain.response.SuccessResult
import kotlinx.coroutines.delay

class LoginUseCase(
    private val userController: UserController
) : UseCase<LoginUseCase.RequestValues, Boolean>() {

    override suspend fun run(requestValues: RequestValues) {
        userController.login(

        )
        println("Thread - ${Thread.currentThread().name}")
        delay(2_000)
        resultChannel.send(SuccessResult(false))
    }

    operator fun invoke()

    class RequestValues(
        val userName: String,
        val password: String
    ) : UseCase.RequestValues
}