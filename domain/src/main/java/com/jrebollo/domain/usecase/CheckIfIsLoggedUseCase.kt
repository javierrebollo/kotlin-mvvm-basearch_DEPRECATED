package com.jrebollo.domain.usecase

import com.jrebollo.domain.controller.UserController
import com.jrebollo.domain.response.Response
import com.jrebollo.domain.response.SuccessResult
import kotlinx.coroutines.delay

class CheckIfIsLoggedUseCase(
    private val userController: UserController
) : UseCase<UseCase.RequestValues, Boolean>() {

    override suspend fun run(requestValues: RequestValues) {
        //Adding drama
        delay(2_000)
        resultChannel.send(SuccessResult(!userController.token.isNullOrEmpty()))
    }

    operator fun invoke(response: Response<Boolean>) {
        execute(
            sEmptyRequestValues,
            response
        )
    }
}