package com.jrebollo.basearch.ui.viewmodel

import com.jrebollo.basearch.ui.base.BaseViewModel
import com.jrebollo.basearch.ui.base.BaseViewModelFactory

class HomeVM : BaseViewModel() {
    override fun start() {

    }
}

class HomeVMFactory : BaseViewModelFactory<HomeVM>() {
    override fun buildViewModel(): HomeVM {
        return HomeVM()
    }
}
