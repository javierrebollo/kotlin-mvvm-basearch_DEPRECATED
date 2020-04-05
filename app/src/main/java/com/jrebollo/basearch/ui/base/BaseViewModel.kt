package com.jrebollo.basearch.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import com.jrebollo.basearch.utils.NavigationCommand
import com.jrebollo.basearch.utils.SingleLiveEvent

abstract class BaseViewModel : ViewModel() {
    protected val TAG: String = this::class.java.simpleName

    private val _navigation: SingleLiveEvent<NavigationCommand> = SingleLiveEvent()
    val navigation
        get() = _navigation

    private val _errorNotifier: SingleLiveEvent<ErrorType> = SingleLiveEvent()
    val errorNotifier
        get() = _errorNotifier

    fun goTo(direction: NavDirections) {
        _navigation.value = NavigationCommand.To(direction)
    }

    fun goBack() {
        _navigation.value = NavigationCommand.Back
    }

    fun goBackTo(directionId: Int) {
        _navigation.value = NavigationCommand.BackTo(directionId)
    }

    fun notifyError(error: ErrorType) {
        _errorNotifier.value = error
    }
}

sealed class ErrorType {
    data class LoginError(val message: String) : ErrorType()
    data class LoadLiveUserError(val message: String) : ErrorType()
}

abstract class BaseViewModelFactory<VM : BaseViewModel> : ViewModelProvider.NewInstanceFactory() {

    abstract fun buildViewModel(): VM

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return buildViewModel() as T
    }
}