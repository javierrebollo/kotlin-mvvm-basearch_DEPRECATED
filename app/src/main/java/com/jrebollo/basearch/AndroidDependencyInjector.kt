package com.jrebollo.basearch

import com.jrebollo.basearch.ui.viewmodel.HomeVMFactory
import com.jrebollo.basearch.ui.viewmodel.LoginVMFactory
import com.jrebollo.basearch.ui.viewmodel.SplashVMFactory
import com.jrebollo.data.controller.UserControllerImpl
import com.jrebollo.data.helper.NetworkStatusHelper
import com.jrebollo.data.helper.SharedPreferencesHelperImpl
import com.jrebollo.data.network.ServerClient
import com.jrebollo.domain.DependencyInjector
import com.jrebollo.domain.Tracker
import com.jrebollo.domain.controller.UserController
import com.jrebollo.domain.helper.SharedPreferencesHelper
import okhttp3.OkHttpClient

object AndroidDependencyInjector : DependencyInjector() {

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder().build()
    }

    private val serverClient: ServerClient by lazy {
        ServerClient(
            okHttpClient,
            provideNetworkStatusHelper(),
            provideTracker()
        )
    }

    //**********************
    //**** VIEW MODELS *****
    //**********************
    fun provideSplashVMFactory(): SplashVMFactory {
        return SplashVMFactory(provideCheckIfIsLoggedUseCase(), provideLoginUseCase())
    }

    fun provideHomeVMFactory(): HomeVMFactory {
        return HomeVMFactory()
    }

    fun provideLoginVMFactory(): LoginVMFactory {
        return LoginVMFactory()
    }

    //*****************
    //**** HELPER *****
    //*****************

    override fun provideSharedPreferencesHelper(): SharedPreferencesHelper {
        return SharedPreferencesHelperImpl(MyApplication.instance)
    }

    override fun provideTracker(): Tracker {
        return TrackerImpl.instance
    }

    private fun provideNetworkStatusHelper(): NetworkStatusHelper {
        return NetworkStatusHelper(MyApplication.instance)
    }

    //*********************
    //**** CONTROLLER *****
    //*********************

    override fun provideUserController(): UserController {
        return UserControllerImpl(
            provideSharedPreferencesHelper(),
            serverClient
        )
    }
}