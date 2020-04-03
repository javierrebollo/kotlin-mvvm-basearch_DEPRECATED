package com.jrebollo.basearch.ui.viewmodel

import android.util.Log
import com.jrebollo.basearch.ui.base.BaseViewModel
import com.jrebollo.basearch.ui.base.BaseViewModelFactory
import com.jrebollo.basearch.ui.view.SplashViewDirections
import com.jrebollo.domain.response.on
import com.jrebollo.domain.usecase.CheckIfIsLoggedUseCase

class SplashVM(
    private val checkIfIsLoggedUseCase: CheckIfIsLoggedUseCase
) : BaseViewModel() {

    init {
        Log.d(TAG, "Splash is started - ${Thread.currentThread().name}")

        checkIfIsLoggedUseCase { taskResult ->
            taskResult.on(
                success = { isLogged ->
                    if (isLogged) {
                        goTo(SplashViewDirections.fromSplashToHome())
                    } else {
                        goTo(SplashViewDirections.fromSplashToLogin())
                    }
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
