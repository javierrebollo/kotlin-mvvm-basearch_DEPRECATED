package com.jrebollo.basearch.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.jrebollo.basearch.DependencyInjector
import com.jrebollo.basearch.databinding.ViewHomeBinding
import com.jrebollo.basearch.ui.base.BaseView
import com.jrebollo.basearch.ui.base.ErrorType
import com.jrebollo.basearch.ui.viewmodel.HomeVM
import com.jrebollo.basearch.ui.viewmodel.HomeVMFactory


class HomeView : BaseView<ViewHomeBinding, HomeVM, HomeVMFactory>() {

    override val viewModel: HomeVM by viewModels {
        buildViewModelFactory()
    }

    override fun buildViewModelFactory(): HomeVMFactory {
        return DependencyInjector.provideHomeVMFactory()
    }

    override fun initComponents(binding: ViewHomeBinding) {

    }

    override fun addListeners(binding: ViewHomeBinding) {

    }

    override fun addObservers(binding: ViewHomeBinding) {
    }

    override fun errorHandler(errorType: ErrorType) {

    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ViewHomeBinding = ViewHomeBinding.inflate(inflater, container, false)
}
