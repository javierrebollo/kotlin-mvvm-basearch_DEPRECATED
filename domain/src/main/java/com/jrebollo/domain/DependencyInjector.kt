package com.jrebollo.domain

import com.jrebollo.domain.helper.SharedPreferencesHelper
import com.jrebollo.domain.usecase.CheckIfIsLoggedUseCase

abstract class DependencyInjector {

    //*******************
    //**** USE CASES ****
    //*******************
    protected fun provideCheckIfIsLoggedUseCase(): CheckIfIsLoggedUseCase {
        return CheckIfIsLoggedUseCase()
    }

    //****************
    //**** HELPER ****
    //****************
    abstract fun provideSharedPreferencesHelper(): SharedPreferencesHelper
}
