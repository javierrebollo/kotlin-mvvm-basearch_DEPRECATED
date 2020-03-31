package com.jrebollo.basearch

import com.jrebollo.basearch.ui.viewmodel.HomeVMFactory
import com.jrebollo.basearch.ui.viewmodel.SplashVMFactory
import com.jrebollo.domain.DependencyInjector

object AndroidDependencyInjector : DependencyInjector() {

    //**********************
    //**** VIEW MODELS *****
    //**********************
    fun provideSplashVMFactory(): SplashVMFactory {
        return SplashVMFactory()
    }

    fun provideHomeVMFactory(): HomeVMFactory {
        return HomeVMFactory()
    }
}