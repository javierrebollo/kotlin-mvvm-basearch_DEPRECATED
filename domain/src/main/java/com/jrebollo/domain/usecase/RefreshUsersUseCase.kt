package com.jrebollo.domain.usecase

import com.jrebollo.domain.controller.UserRepository
import com.jrebollo.domain.entity.User
import com.jrebollo.domain.response.TaskResult
import kotlinx.coroutines.channels.ReceiveChannel
import java.util.*

class RefreshUsersUseCase(
    private val userRepository: UserRepository
) : UseCase<UseCase.RequestValues, Boolean>() {

    override suspend fun run(requestValues: RequestValues) {
        runIOBlock {
            userRepository.addUser(
                generateFakeUser()
            )
            sendSuccess(true)
        }
    }

    operator fun invoke(): ReceiveChannel<TaskResult<Boolean>> {
        return execute(
            sEmptyRequestValues
        )
    }

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
}