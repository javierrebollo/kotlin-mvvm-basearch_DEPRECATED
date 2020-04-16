package com.jrebollo.basearch.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jrebollo.basearch.data.entity.User
import com.jrebollo.basearch.data.repository.UserRepository
import com.jrebollo.basearch.exception.safeLocalizedMessage
import com.jrebollo.basearch.ui.base.BaseViewModel
import com.jrebollo.basearch.ui.base.BaseViewModelFactory
import com.jrebollo.basearch.ui.base.ErrorType
import com.jrebollo.basearch.ui.view.HomeViewDirections
import com.jrebollo.basearch.utils.extensions.on
import kotlinx.coroutines.launch

class HomeVM(
    val userRepository: UserRepository
) : BaseViewModel() {

    var liveDataUsers: LiveData<List<User>>? = userRepository.liveUsers

    override fun loadData() {

    }

    fun refreshUsers() {
        viewModelScope.launch {
            userRepository.loadNewUser()
                .on(
                    loading = {
                        showLoading()
                    },
                    success = {
                        hideLoading()
                    },
                    failure = {
                        hideLoading()
                        notifyError(ErrorType.GenericError(it.safeLocalizedMessage))
                    }
                )
        }
    }

    fun openUserDetails(user: User) {
        goTo(HomeViewDirections.fromHomeToUserDetail(user))
    }
}

class HomeVMFactory(
    private val userRepository: UserRepository
) : BaseViewModelFactory<HomeVM>() {
    override fun buildViewModel(): HomeVM {
        return HomeVM(userRepository)
    }
}
