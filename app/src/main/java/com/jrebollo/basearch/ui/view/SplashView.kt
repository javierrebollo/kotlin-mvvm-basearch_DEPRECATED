package com.jrebollo.basearch.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.jrebollo.basearch.DependencyInjector
import com.jrebollo.basearch.databinding.ViewSplashBinding
import com.jrebollo.basearch.ui.base.BaseView
import com.jrebollo.basearch.ui.base.ErrorType
import com.jrebollo.basearch.ui.viewmodel.SplashVM
import com.jrebollo.basearch.ui.viewmodel.SplashVMFactory


class SplashView : BaseView<ViewSplashBinding, SplashVM, SplashVMFactory>() {
    override val viewModel: SplashVM by viewModels {
        buildViewModelFactory()
    }

    override fun buildViewModelFactory(): SplashVMFactory {
        return DependencyInjector.provideSplashVMFactory()
    }

    override fun initComponents(binding: ViewSplashBinding) {
    }

    override fun addListeners(binding: ViewSplashBinding) {
    }

    override fun addObservers(binding: ViewSplashBinding) {
    }

    override fun errorHandler(errorType: ErrorType) {

    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ViewSplashBinding = ViewSplashBinding.inflate(inflater, container, false)
}
