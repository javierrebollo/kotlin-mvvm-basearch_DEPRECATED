package com.jrebollo.basearch.ui.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.jrebollo.basearch.data.repository.UserRepository
import com.jrebollo.basearch.ui.base.BaseViewModel
import com.jrebollo.basearch.ui.base.BaseViewModelFactory

class LoginVM(
    private val userRepository: UserRepository
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
//        loginUseCase(usernameLiveData.value ?: "", passwordLiveData.value ?: "") {
//            it.on(
//                success = { loginSuccess ->
//                    if (loginSuccess) {
//                        goTo(LoginViewDirections.fromLoginToHome())
//                    } else {
//                        notifyError(ErrorType.LoginError(R.string.invalid_username_or_password.getString()))
//                    }
//                },
//                failure = {
//                    notifyError(ErrorType.LoginError(R.string.invalid_username_or_password.getString()))
//                }
//            )
//        }
    }
}

class LoginVMFactory(
    private val userRepository: UserRepository
) : BaseViewModelFactory<LoginVM>() {
    override fun buildViewModel(): LoginVM {
        return LoginVM(userRepository)
    }
}
