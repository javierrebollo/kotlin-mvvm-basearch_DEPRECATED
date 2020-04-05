package com.jrebollo.basearch.ui.viewmodel

import com.jrebollo.basearch.R
import com.jrebollo.basearch.ui.base.BaseViewModel
import com.jrebollo.basearch.ui.base.BaseViewModelFactory
import com.jrebollo.basearch.ui.base.ErrorType
import com.jrebollo.basearch.utils.extensions.getString
import com.jrebollo.domain.entity.User
import com.jrebollo.domain.helper.LiveDataHandler
import com.jrebollo.domain.response.on
import com.jrebollo.domain.usecase.GetAllUsersUseCase

class HomeVM(
    val getAllUsersUseCase: GetAllUsersUseCase
) : BaseViewModel() {

    var liveDataUsers: LiveDataHandler<List<User>>? = null

    init {
        getAllUsersUseCase {
            it.on(
                success = { liveDataHandler ->
                    liveDataUsers = liveDataHandler.o
                },
                failure = {
                    notifyError(ErrorType.LoadLiveUserError(R.string.error_default.getString()))
                }
            )
        }
    }

}

class HomeVMFactory(
    private val getAllUsersUseCase: GetAllUsersUseCase
) : BaseViewModelFactory<HomeVM>() {
    override fun buildViewModel(): HomeVM {
        return HomeVM(getAllUsersUseCase)
    }
}
