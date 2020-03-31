package com.jrebollo.basearch.ui.viewmodel

import android.util.Log
import com.jrebollo.basearch.ui.base.BaseViewModel
import com.jrebollo.basearch.ui.base.BaseViewModelFactory

class SplashVM : BaseViewModel() {
    override fun start() {
        Log.d(TAG, "Splash is started")
    }
}

class SplashVMFactory : BaseViewModelFactory<SplashVM>() {
    override fun buildViewModel(): SplashVM {
        return SplashVM()
    }
}
