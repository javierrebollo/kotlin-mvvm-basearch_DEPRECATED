package com.jrebollo.basearch

import com.jrebollo.basearch.data.db.AppDatabase
import com.jrebollo.basearch.data.db.dao.UserDao
import com.jrebollo.basearch.data.helper.NetworkStatusHelper
import com.jrebollo.basearch.data.helper.SharedPreferencesHelper
import com.jrebollo.basearch.data.network.ServerClient
import com.jrebollo.basearch.data.repository.UserRepository
import com.jrebollo.basearch.ui.viewmodel.HomeVMFactory
import com.jrebollo.basearch.ui.viewmodel.LoginVMFactory
import com.jrebollo.basearch.ui.viewmodel.SplashVMFactory
import com.jrebollo.basearch.ui.viewmodel.UserDetailVMFactory
import okhttp3.OkHttpClient

object DependencyInjector {

    private val applicationContext = MyApplication.instance.applicationContext
    private val database
        get() = AppDatabase.getInstance(applicationContext)

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder().build()
    }

    private val serverClient: ServerClient by lazy {
        ServerClient(
            okHttpClient,
            provideNetworkStatusHelper()
        )
    }

    //**********************
    //**** VIEW MODELS *****
    //**********************
    fun provideUserDetailVMFactory(): UserDetailVMFactory {
        return UserDetailVMFactory()
    }

    fun provideSplashVMFactory(): SplashVMFactory {
        return SplashVMFactory(
            provideUserRepository()
        )
    }

    fun provideHomeVMFactory(): HomeVMFactory {
        return HomeVMFactory(
            provideUserRepository()
        )
    }

    fun provideLoginVMFactory(): LoginVMFactory {
        return LoginVMFactory(
            provideUserRepository()
        )
    }

    //*****************
    //**** HELPER *****
    //*****************

    private fun provideSharedPreferencesHelper(): SharedPreferencesHelper {
        return SharedPreferencesHelper(applicationContext)
    }

    private fun provideNetworkStatusHelper(): NetworkStatusHelper {
        return NetworkStatusHelper(applicationContext)
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

    fun provideUserRepository(): UserRepository {
        return UserRepository.getInstance(
            provideSharedPreferencesHelper(),
            serverClient,
            provideUserDao()
        )
    }
}