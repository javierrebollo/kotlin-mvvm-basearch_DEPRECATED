package com.jrebollo.basearch.ui.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.jrebollo.basearch.R
import com.jrebollo.basearch.ui.base.BaseViewModel
import com.jrebollo.basearch.ui.base.BaseViewModelFactory
import com.jrebollo.basearch.ui.base.ErrorType
import com.jrebollo.basearch.ui.view.LoginViewDirections
import com.jrebollo.basearch.utils.extensions.getString
import com.jrebollo.domain.response.on
import com.jrebollo.domain.usecase.LoginUseCase

class LoginVM(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {
    val usernameLiveData: MutableLiveData<String> = MutableLiveData()
    val passwordLiveData: MutableLiveData<String> = MutableLiveData()

    val enableLoginButton: MediatorLiveData<Boolean> = MediatorLiveData()

    init {
        enableLoginButton.addSource(usernameLiveData) {
            enableLoginButton.value = passwordLiveData.value?.isNotBlank() == true && it?.isNotBlank() ?: false
        }
        enableLoginButton.addSource(passwordLiveData) {
            enableLoginButton.value = usernameLiveData.value?.isNotBlank() == true && it?.isNotBlank() ?: false
        }
    }

    fun login() {
        loginUseCase(usernameLiveData.value ?: "", passwordLiveData.value ?: "") {
            it.on(
                success = { loginSuccess ->
                    if (loginSuccess) {
                        goTo(LoginViewDirections.fromLoginToHome())
                    } else {
                        notifyError(ErrorType.LoginError(R.string.invalid_username_or_password.getString()))
                    }
                },
                failure = {
                    notifyError(ErrorType.LoginError(R.string.invalid_username_or_password.getString()))
                }
            )
        }
    }
}

class LoginVMFactory(
    private val loginUseCase: LoginUseCase
) : BaseViewModelFactory<LoginVM>() {
    override fun buildViewModel(): LoginVM {
        return LoginVM(loginUseCase)
    }
}
