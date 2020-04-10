package com.jrebollo.domain

import com.jrebollo.domain.controller.UserRepository
import com.jrebollo.domain.helper.LiveDataHandlerDispatcher
import com.jrebollo.domain.helper.SharedPreferencesHelper
import com.jrebollo.domain.usecase.*

abstract class DependencyInjector {

    //*******************
    //**** USE CASES ****
    //*******************
    protected fun provideCheckIfIsLoggedUseCase(): CheckIfIsLoggedUseCase {
        return complete(CheckIfIsLoggedUseCase(provideUserRepository()))
    }

    protected fun provideLoginUseCase(): LoginUseCase {
        return complete(LoginUseCase(provideUserRepository()))
    }

    protected fun provideRefreshUsersUseCase(): RefreshUsersUseCase {
        return complete(RefreshUsersUseCase(provideUserRepository()))
    }

    //*****************************
    //**** LIVE DATA USE CASES ****
    //*****************************

    protected fun provideGetAllUsersLDUseCase(): GetAllUsersLDUseCase {
        return GetAllUsersLDUseCase(provideUserRepository())
    }

    //****************
    //**** HELPER ****
    //****************
    abstract fun provideSharedPreferencesHelper(): SharedPreferencesHelper
    abstract fun provideTracker(): Tracker
    abstract fun provideLiveDataHandlerDispatcher(): LiveDataHandlerDispatcher


    //**********************
    //**** SYNTAX SUGAR ****
    //**********************

    private fun <U : UseCase<*, *>> complete(useCase: U): U {
        useCase.tracker = provideTracker()
        return useCase
    }

    //*********************
    //**** REPOSITORY *****
    //*********************
    abstract fun provideUserRepository(): UserRepository
}
