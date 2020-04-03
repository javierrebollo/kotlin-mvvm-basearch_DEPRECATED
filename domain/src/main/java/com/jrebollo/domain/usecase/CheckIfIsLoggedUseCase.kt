package com.jrebollo.domain.usecase

import com.jrebollo.domain.response.Response
import com.jrebollo.domain.response.SuccessResult
import kotlinx.coroutines.delay

class CheckIfIsLoggedUseCase : UseCase<UseCase.RequestValues, Boolean>() {

    override suspend fun run(requestValues: RequestValues) {
        println("Thread - ${Thread.currentThread().name}")
        delay(2_000)
        resultChannel.send(SuccessResult(false))
    }

    operator fun invoke(response: Response<Boolean>) {
        execute(
            sEmptyRequestValues,
            response
        )
    }
}