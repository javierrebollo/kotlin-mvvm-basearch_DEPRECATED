package com.jrebollo.domain.usecase

import com.jrebollo.domain.controller.UserController
import com.jrebollo.domain.response.Response
import com.jrebollo.domain.response.on

class LoginUseCase(
    private val userController: UserController
) : UseCase<LoginUseCase.RequestValues, String>() {

    override suspend fun run(requestValues: RequestValues) {
        userController.login(
            requestValues.userName,
            requestValues.password
        ).on(
            success = {
                sendSuccess(it)
            },
            failure = {
                sendError(it)
            }
        )
    }

    operator fun invoke(userName: String, password: String, response: Response<String>) {
        execute(
            RequestValues(
                userName,
                password
            ),
            response
        )
    }

    class RequestValues(
        val userName: String,
        val password: String
    ) : UseCase.RequestValues
}