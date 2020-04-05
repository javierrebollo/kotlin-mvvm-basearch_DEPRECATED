package com.jrebollo.basearch.ui.viewmodel

import androidx.lifecycle.LiveData
import com.jrebollo.basearch.data.entity.User
import com.jrebollo.basearch.data.repository.UserRepository
import com.jrebollo.basearch.ui.base.BaseViewModel
import com.jrebollo.basearch.ui.base.BaseViewModelFactory

class HomeVM(
    val userRepository: UserRepository
) : BaseViewModel() {
    var liveDataUsers: LiveData<List<User>>? = null

    override fun loadData() {

    }
}

class HomeVMFactory(
    private val userRepository: UserRepository
) : BaseViewModelFactory<HomeVM>() {
    override fun buildViewModel(): HomeVM {
        return HomeVM(userRepository)
    }
}
