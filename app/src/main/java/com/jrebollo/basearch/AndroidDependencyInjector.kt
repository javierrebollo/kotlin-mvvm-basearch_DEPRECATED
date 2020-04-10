package com.jrebollo.basearch

import com.jrebollo.basearch.ui.viewmodel.HomeVMFactory
import com.jrebollo.basearch.ui.viewmodel.LoginVMFactory
import com.jrebollo.basearch.ui.viewmodel.SplashVMFactory
import com.jrebollo.data.db.AppDatabase
import com.jrebollo.data.db.dao.UserDao
import com.jrebollo.data.helper.LiveDataHandlerDispatcherImpl
import com.jrebollo.data.helper.NetworkStatusHelper
import com.jrebollo.data.helper.SharedPreferencesHelperImpl
import com.jrebollo.data.network.ServerClient
import com.jrebollo.data.repository.UserRepositoryImpl
import com.jrebollo.domain.DependencyInjector
import com.jrebollo.domain.Tracker
import com.jrebollo.domain.controller.UserRepository
import com.jrebollo.domain.helper.LiveDataHandlerDispatcher
import com.jrebollo.domain.helper.SharedPreferencesHelper
import okhttp3.OkHttpClient

object AndroidDependencyInjector : DependencyInjector() {

    private val applicationContext = MyApplication.instance.applicationContext
    private val database
        get() = AppDatabase.getInstance(applicationContext)

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
        return SplashVMFactory(provideCheckIfIsLoggedUseCase())
    }

    fun provideHomeVMFactory(): HomeVMFactory {
        return HomeVMFactory(
            provideGetAllUsersLDUseCase(),
            provideRefreshUsersUseCase()
        )
    }

    fun provideLoginVMFactory(): LoginVMFactory {
        return LoginVMFactory(provideLoginUseCase())
    }

    //*****************
    //**** HELPER *****
    //*****************

    override fun provideSharedPreferencesHelper(): SharedPreferencesHelper {
        return SharedPreferencesHelperImpl(applicationContext)
    }

    override fun provideTracker(): Tracker {
        return TrackerImpl.instance
    }

    private fun provideNetworkStatusHelper(): NetworkStatusHelper {
        return NetworkStatusHelper(applicationContext)
    }

    override fun provideLiveDataHandlerDispatcher(): LiveDataHandlerDispatcher {
        return LiveDataHandlerDispatcherImpl()
    }

    //**************
    //**** DAO *****
    //**************
    private fun provideUserDao(): UserDao {
        return database.userDao()
    }

    //*********************
    //**** REPOSITORY *****
    //*********************

    override fun provideUserRepository(): UserRepository {
        return UserRepositoryImpl.getInstance(
            provideSharedPreferencesHelper(),
            serverClient,
            provideUserDao()
        )
    }
}