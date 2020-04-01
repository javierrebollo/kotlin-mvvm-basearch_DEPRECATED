package com.jrebollo.basearch.ui.viewmodel

import android.util.Log
import com.jrebollo.basearch.ui.base.BaseViewModel
import com.jrebollo.basearch.ui.base.BaseViewModelFactory
import com.jrebollo.basearch.ui.view.SplashViewDirections
import kotlinx.coroutines.*

class SplashVM : BaseViewModel() {
    override fun start() {
        Log.d(TAG, "Splash is started")

        GlobalScope.launch {
            Log.d(TAG, "Start")
            withContext(Dispatchers.Default) {
                delay(2_000)
            }

            Log.d(TAG, "Finished")
            withContext(Dispatchers.Main) {
                goTo(SplashViewDirections.fromSplashToHome())
            }
        }
    }
}

class SplashVMFactory : BaseViewModelFactory<SplashVM>() {
    override fun buildViewModel(): SplashVM {
        return SplashVM()
    }
}
