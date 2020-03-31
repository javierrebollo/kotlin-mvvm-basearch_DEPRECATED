package com.jrebollo.basearch.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseViewModel : ViewModel() {
    val TAG: String = this.javaClass.simpleName
    abstract fun start()
}

abstract class BaseViewModelFactory<VM : BaseViewModel> : ViewModelProvider.NewInstanceFactory() {

    abstract fun buildViewModel(): VM

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return buildViewModel() as T
    }
}