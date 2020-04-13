package com.jrebollo.basearch.ui.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jrebollo.basearch.ui.base.BaseViewModel
import com.jrebollo.basearch.ui.base.BaseViewModelFactory
import com.jrebollo.domain.response.TaskResult
import com.jrebollo.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

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
        launchDataLoad {

        }
    }

    fun login() {
        viewModelScope.launch {
            flow<TaskResult<Boolean>> {
                emit(TaskResult.Loading())

            }.collect { value ->

            }
        }

//        viewModelScope.launch {
//            loginUseCase(usernameLiveData.value ?: "", passwordLiveData.value ?: "").consumeEach {taskResult->
//                when(taskResult){
//                    is TaskResult.Loading -> loading.invoke()
//                    is TaskResult.SuccessResult -> success(taskResult.value)
//                    is TaskResult.ErrorResult -> failure(taskResult.exception)
//                }
//            }on(
//                loading = {loginSuccess ->
//                    if (loginSuccess) {
//                        goTo(LoginViewDirections.fromLoginToHome())
//                    } else {
//                        notifyError(ErrorType.LoginError(R.string.invalid_username_or_password.getString()))
//                    }
//                },
//             success = {},
//                failure = {}
//            ) {
//                it.on(
//                    success = { loginSuccess ->
//
//                    },
//                    failure = {
//                        notifyError(ErrorType.LoginError(R.string.invalid_username_or_password.getString()))
//                    }
//                )
//            }
//        }

    }
}

class LoginVMFactory(
    private val loginUseCase: LoginUseCase
) : BaseViewModelFactory<LoginVM>() {
    override fun buildViewModel(): LoginVM {
        return LoginVM(loginUseCase)
    }
}
