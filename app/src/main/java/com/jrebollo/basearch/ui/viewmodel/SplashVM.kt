package com.jrebollo.basearch.ui.viewmodel

import android.util.Log
import com.jrebollo.basearch.ui.base.BaseViewModel
import com.jrebollo.basearch.ui.base.BaseViewModelFactory
import com.jrebollo.domain.response.on
import com.jrebollo.domain.usecase.CheckIfIsLoggedUseCase

class SplashVM(
    _checkIfIsLoggedUseCase: CheckIfIsLoggedUseCase
) : BaseViewModel() {
    val checkIfIsLoggedUseCase: CheckIfIsLoggedUseCase = _checkIfIsLoggedUseCase

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
    }
}

class SplashVMFactory(
    private val checkIfIsLoggedUseCase: CheckIfIsLoggedUseCase
) : BaseViewModelFactory<SplashVM>() {
    override fun buildViewModel(): SplashVM {
        return SplashVM(checkIfIsLoggedUseCase)
    }
}
