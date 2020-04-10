package com.jrebollo.basearch.ui.viewmodel

import com.jrebollo.basearch.ui.base.BaseViewModel
import com.jrebollo.basearch.ui.base.BaseViewModelFactory
import com.jrebollo.domain.entity.User
import com.jrebollo.domain.helper.LiveDataHandler
import com.jrebollo.domain.response.on
import com.jrebollo.domain.usecase.GetAllUsersLDUseCase
import com.jrebollo.domain.usecase.RefreshUsersUseCase

class HomeVM(
    private val getAllUsersLDUseCase: GetAllUsersLDUseCase,
    private val refreshUsersUseCase: RefreshUsersUseCase
) : BaseViewModel() {

    var liveDataUsers: LiveDataHandler<List<User>>? = getAllUsersLDUseCase()

    fun refreshUsers() {
        refreshUsersUseCase {
            it.on(
                success = {

                },
                failure = {

                }
            )
        }
    }
}

class HomeVMFactory(
    private val getAllUsersLDUseCase: GetAllUsersLDUseCase,
    private val refreshUsersUseCase: RefreshUsersUseCase
) : BaseViewModelFactory<HomeVM>() {
    override fun buildViewModel(): HomeVM {
        return HomeVM(getAllUsersLDUseCase, refreshUsersUseCase)
    }
}
