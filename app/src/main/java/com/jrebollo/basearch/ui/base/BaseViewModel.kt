package com.jrebollo.basearch.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import com.jrebollo.basearch.utils.NavigationCommand
import com.jrebollo.basearch.utils.SingleLiveEvent

abstract class BaseViewModel : ViewModel() {
    val TAG: String = this.javaClass.simpleName
    private val _navigation: SingleLiveEvent<NavigationCommand> = SingleLiveEvent()
    val navigation
        get() = _navigation

    abstract fun start()

    fun goTo(direction: NavDirections) {
        _navigation.value = NavigationCommand.To(direction)
    }

    fun goBack() {
        _navigation.value = NavigationCommand.Back
    }

    fun goBackTo(directionId: Int) {
        _navigation.value = NavigationCommand.BackTo(directionId)
    }
}

abstract class BaseViewModelFactory<VM : BaseViewModel> : ViewModelProvider.NewInstanceFactory() {

    abstract fun buildViewModel(): VM

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return buildViewModel() as T
    }
}