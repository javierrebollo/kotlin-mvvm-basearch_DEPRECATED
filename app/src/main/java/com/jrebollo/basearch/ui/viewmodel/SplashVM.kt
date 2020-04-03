package com.jrebollo.basearch.ui.viewmodel

import android.util.Log
import com.jrebollo.basearch.ui.base.BaseViewModel
import com.jrebollo.basearch.ui.base.BaseViewModelFactory
import com.jrebollo.domain.response.on
import com.jrebollo.domain.usecase.CheckIfIsLoggedUseCase
import com.jrebollo.domain.usecase.LoginUseCase

class SplashVM(
    _checkIfIsLoggedUseCase: CheckIfIsLoggedUseCase,
    _loginUseCase: LoginUseCase
) : BaseViewModel() {
    private val checkIfIsLoggedUseCase: CheckIfIsLoggedUseCase = _checkIfIsLoggedUseCase
    private val loginUseCase: LoginUseCase = _loginUseCase

    override fun start() {
        Log.d(TAG, "Splash is started - ${Thread.currentThread().name}")

        checkIfIsLoggedUseCase { taskResult ->
            taskResult.on(
                success = {
                    println("Success - ${Thread.currentThread().name}")
                },
                failure = {
                    println("Failure - ${Thread.currentThread().name}")
                }
            )
        }

        loginUseCase.invoke("fake", "fake") {
            it.on(
                success = { response ->
                    println("Success: $response")
                },
                failure = { exception ->
                    println("Exception: ${exception.message}")
                }
            )
        }
    }
}

class SplashVMFactory(
    private val checkIfIsLoggedUseCase: CheckIfIsLoggedUseCase,
    private val loginUseCase: LoginUseCase
) : BaseViewModelFactory<SplashVM>() {
    override fun buildViewModel(): SplashVM {
        return SplashVM(checkIfIsLoggedUseCase, loginUseCase)
    }
}
