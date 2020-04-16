package com.jrebollo.basearch.ui.viewmodel

import com.jrebollo.basearch.ui.base.BaseViewModel
import com.jrebollo.basearch.ui.base.BaseViewModelFactory

class UserDetailVM : BaseViewModel() {

    override fun loadData() {

    }
}

class UserDetailVMFactory() : BaseViewModelFactory<UserDetailVM>() {
    override fun buildViewModel(): UserDetailVM {
        return UserDetailVM()
    }
}

