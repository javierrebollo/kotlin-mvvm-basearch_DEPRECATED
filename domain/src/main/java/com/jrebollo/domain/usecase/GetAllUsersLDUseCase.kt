package com.jrebollo.domain.usecase

import com.jrebollo.domain.controller.UserRepository
import com.jrebollo.domain.entity.User
import com.jrebollo.domain.helper.LiveDataHandler

class GetAllUsersLDUseCase(
    private val userRepository: UserRepository
) : LiveDataUseCase<UseCase.RequestValues, List<User>>() {

    override fun run(requestValues: UseCase.RequestValues): LiveDataHandler<List<User>> {
        return userRepository.liveUsers
    }

    operator fun invoke(): LiveDataHandler<List<User>> {
        return execute(
            sEmptyRequestValues
        )
    }
}