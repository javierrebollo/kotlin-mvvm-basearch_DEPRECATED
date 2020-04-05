package com.jrebollo.basearch.ui.viewmodel

import com.jrebollo.basearch.data.repository.UserRepository
import com.jrebollo.basearch.ui.base.BaseViewModel
import com.jrebollo.basearch.ui.base.BaseViewModelFactory
import com.jrebollo.basearch.ui.view.SplashViewDirections

class SplashVM(
    private val userRepository: UserRepository
) : BaseViewModel() {

    override fun loadData() {
        if (userRepository.isLogged) {
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
