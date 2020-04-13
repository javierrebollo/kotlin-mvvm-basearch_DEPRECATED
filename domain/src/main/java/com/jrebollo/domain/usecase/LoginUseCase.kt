package com.jrebollo.domain.usecase

import com.jrebollo.domain.controller.UserRepository
import com.jrebollo.domain.exception.safeLocalizedMessage
import com.jrebollo.domain.response.TaskResult
import com.jrebollo.domain.response.on
import kotlinx.coroutines.channels.ReceiveChannel

class LoginUseCase(
    private val userRepository: UserRepository
) : UseCase<LoginUseCase.RequestValues, Boolean>() {

    override suspend fun run(requestValues: RequestValues) {
        runIOBlock {
            userRepository.login(
                requestValues.userName,
                requestValues.password
            ).on(
                success = {
                    userRepository.token = it
                    sendSuccess(true)
                },
                failure = {
                    tracker.logDebug(TAG, it.safeLocalizedMessage)
                    sendError(it)
                }
            )
        }
    }

    operator fun invoke(userName: String, password: String): ReceiveChannel<TaskResult<Boolean>> {
        return execute(
            RequestValues(
                userName,
                password
            )
        )
    }

    class RequestValues(
        val userName: String,
        val password: String
    ) : UseCase.RequestValues
}