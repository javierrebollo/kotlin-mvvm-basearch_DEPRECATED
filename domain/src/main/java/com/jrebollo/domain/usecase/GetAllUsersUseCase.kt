package com.jrebollo.domain.usecase

import com.jrebollo.domain.controller.UserRepository
import com.jrebollo.domain.entity.User
import com.jrebollo.domain.helper.LiveDataHandler
import com.jrebollo.domain.response.Response

class GetAllUsersUseCase(
    private val userRepository: UserRepository
) : UseCase<UseCase.RequestValues, LiveDataHandler<List<User>>>() {

    override suspend fun run(requestValues: RequestValues) {
        sendSuccess(userRepository.liveUsers)
    }

    operator fun invoke(response: Response<LiveDataHandler<List<User>>>) {
        execute(
            sEmptyRequestValues,
            response
        )
    }
}