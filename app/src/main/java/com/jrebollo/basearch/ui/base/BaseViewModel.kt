package com.jrebollo.basearch.ui.base

import androidx.annotation.UiThread
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

    private val _loadingState: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val loadingState
        get() = _loadingState

    private val _forceKeyboardState: SingleLiveEvent<KeyboardState> = SingleLiveEvent()
    val forceKeyboardState
        get() = _forceKeyboardState

    @UiThread
    fun goTo(direction: NavDirections) {
        _navigation.value = NavigationCommand.To(direction)
    }

    @UiThread
    fun goBack() {
        _navigation.value = NavigationCommand.Back
    }

    @UiThread
    fun goBackTo(directionId: Int) {
        _navigation.value = NavigationCommand.BackTo(directionId)
    }

    @UiThread
    fun notifyError(error: ErrorType) {
        _errorNotifier.value = error
    }

    @UiThread
    fun showLoading() {
        _loadingState.value = true
    }

    @UiThread
    fun hideLoading() {
        _loadingState.value = false
    }

    @UiThread
    fun forceCloseKeyboard() {
        _forceKeyboardState.value = KeyboardState.HIDE
    }

    @UiThread
    fun forceOpenKeyboard() {
        _forceKeyboardState.value = KeyboardState.SHOW
    }

    abstract fun loadData()
}

enum class KeyboardState {
    HIDE, SHOW
}

sealed class ErrorType {
    data class GenericError(val message: String) : ErrorType()
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