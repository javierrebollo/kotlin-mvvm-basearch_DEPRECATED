package com.jrebollo.basearch

import com.jrebollo.basearch.ui.viewmodel.HomeVMFactory
import com.jrebollo.basearch.ui.viewmodel.SplashVMFactory
import com.jrebollo.data.helper.SharedPreferencesHelperImpl
import com.jrebollo.domain.DependencyInjector
import com.jrebollo.domain.helper.SharedPreferencesHelper

object AndroidDependencyInjector : DependencyInjector() {

    //**********************
    //**** VIEW MODELS *****
    //**********************
    fun provideSplashVMFactory(): SplashVMFactory {
        return SplashVMFactory(provideCheckIfIsLoggedUseCase())
    }

    fun provideHomeVMFactory(): HomeVMFactory {
        return HomeVMFactory()
    }

    //*****************
    //**** HELPER *****
    //*****************

    override fun provideSharedPreferencesHelper(): SharedPreferencesHelper {
        return SharedPreferencesHelperImpl(MyApplication.instance)
    }
}