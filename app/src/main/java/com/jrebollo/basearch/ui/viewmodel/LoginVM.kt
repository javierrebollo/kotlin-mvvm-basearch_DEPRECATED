package com.jrebollo.basearch.ui.viewmodel

import com.jrebollo.basearch.ui.base.BaseViewModel
import com.jrebollo.basearch.ui.base.BaseViewModelFactory

class LoginVM : BaseViewModel() {
    override fun start() {

    }
}

class LoginVMFactory : BaseViewModelFactory<LoginVM>() {
    override fun buildViewModel(): LoginVM {
        return LoginVM()
    }
}
