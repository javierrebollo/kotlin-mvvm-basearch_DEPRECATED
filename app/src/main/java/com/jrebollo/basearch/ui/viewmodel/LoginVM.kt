package com.jrebollo.basearch.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jrebollo.basearch.R
import com.jrebollo.basearch.data.repository.UserRepository
import com.jrebollo.basearch.ui.base.BaseViewModel
import com.jrebollo.basearch.ui.base.BaseViewModelFactory
import com.jrebollo.basearch.ui.base.ErrorType
import com.jrebollo.basearch.ui.view.LoginViewDirections
import com.jrebollo.basearch.utils.extensions.getString
import com.jrebollo.basearch.utils.extensions.on
import kotlinx.coroutines.launch

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
        viewModelScope.launch {
            userRepository.login(usernameLiveData.value ?: "", passwordLiveData.value ?: "")
                .on(
                    loading = {
                        Log.d(TAG, "Loading...")
                        showLoading()
                    },
                    success = {
                        hideLoading()
                        goTo(LoginViewDirections.fromLoginToHome())
                    },
                    failure = {
                        hideLoading()
                        notifyError(ErrorType.LoginError(R.string.error_invalid_username_or_password.getString()))
                    }
                )
        }
    }
}

class LoginVMFactory(
    private val userRepository: UserRepository
) : BaseViewModelFactory<LoginVM>() {
    override fun buildViewModel(): LoginVM {
        return LoginVM(userRepository)
    }
}
