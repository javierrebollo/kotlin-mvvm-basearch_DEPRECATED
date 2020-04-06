package com.jrebollo.basearch.ui.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jrebollo.basearch.R
import com.jrebollo.basearch.data.network.on
import com.jrebollo.basearch.data.repository.UserRepository
import com.jrebollo.basearch.ui.base.BaseViewModel
import com.jrebollo.basearch.ui.base.BaseViewModelFactory
import com.jrebollo.basearch.ui.base.ErrorType
import com.jrebollo.basearch.ui.view.LoginViewDirections
import com.jrebollo.basearch.utils.extensions.getString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginVM(
    private val userRepository: UserRepository
) : BaseViewModel() {
    val usernameLiveData: MutableLiveData<String> = MutableLiveData()
    val passwordLiveData: MutableLiveData<String> = MutableLiveData()

    val enableLoginButton: MediatorLiveData<Boolean> = MediatorLiveData()

    override fun loadData() {
        enableLoginButton.addSource(usernameLiveData) {
            enableLoginButton.value = passwordLiveData.value?.isNotBlank() == true && it?.isNotBlank() ?: false
        }
        enableLoginButton.addSource(passwordLiveData) {
            enableLoginButton.value = usernameLiveData.value?.isNotBlank() == true && it?.isNotBlank() ?: false
        }
    }

    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            println("Launch: Thread is - ${Thread.currentThread().name}")
            val response = userRepository.login(usernameLiveData.value ?: "", passwordLiveData.value ?: "")

            withContext(Dispatchers.Default) {
                println("withContext: Thread is - ${Thread.currentThread().name}")
                response.on(
                    success = {
                        println("Success: Thread is - ${Thread.currentThread().name}")
                        goTo(LoginViewDirections.fromLoginToHome())
                    },
                    failure = {
                        println("Failure: Thread is - ${Thread.currentThread().name}")
                        notifyError(ErrorType.LoginError(R.string.error_invalid_username_or_password.getString()))
                    }
                )
            }
        }
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
