package com.jrebollo.domain

import com.jrebollo.domain.controller.UserRepository
import com.jrebollo.domain.helper.SharedPreferencesHelper
import com.jrebollo.domain.usecase.CheckIfIsLoggedUseCase
import com.jrebollo.domain.usecase.GetAllUsersUseCase
import com.jrebollo.domain.usecase.LoginUseCase
import com.jrebollo.domain.usecase.UseCase

abstract class DependencyInjector {

    //*******************
    //**** USE CASES ****
    //*******************
    protected fun provideCheckIfIsLoggedUseCase(): CheckIfIsLoggedUseCase {
        return withTracker(CheckIfIsLoggedUseCase(provideUserRepository()))
    }

    protected fun provideLoginUseCase(): LoginUseCase {
        return withTracker(LoginUseCase(provideUserRepository()))
    }

    protected fun provideGetAllUsersUseCase(): GetAllUsersUseCase {
        return GetAllUsersUseCase(provideUserRepository())
    }

    //****************
    //**** HELPER ****
    //****************
    abstract fun provideSharedPreferencesHelper(): SharedPreferencesHelper
    abstract fun provideTracker(): Tracker


    //**********************
    //**** SYNTAX SUGAR ****
    //**********************

    private fun <U : UseCase<*, *>> withTracker(useCase: U): U {
        useCase.tracker = provideTracker()
        return useCase
    }

    //*********************
    //**** REPOSITORY *****
    //*********************
    abstract fun provideUserRepository(): UserRepository
}
