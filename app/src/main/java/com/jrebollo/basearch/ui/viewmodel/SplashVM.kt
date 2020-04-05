package com.jrebollo.basearch.ui.viewmodel

import android.util.Log
import com.jrebollo.basearch.data.repository.UserRepository
import com.jrebollo.basearch.ui.base.BaseViewModel
import com.jrebollo.basearch.ui.base.BaseViewModelFactory
import com.jrebollo.basearch.ui.view.SplashViewDirections

class SplashVM(
    private val userRepository: UserRepository
) : BaseViewModel() {

    init {
        Log.d(TAG, "Splash is started - ${Thread.currentThread().name}")

        if (true) {
            goTo(SplashViewDirections.fromSplashToHome())
        } else {
            goTo(SplashViewDirections.fromSplashToLogin())
        }
    }
}

class SplashVMFactory(
    private val userRepository: UserRepository
) : BaseViewModelFactory<SplashVM>() {
    override fun buildViewModel(): SplashVM {
        return SplashVM(userRepository)
    }
}
