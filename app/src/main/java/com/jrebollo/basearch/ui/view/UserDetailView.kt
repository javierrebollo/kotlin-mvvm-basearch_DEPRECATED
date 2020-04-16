package com.jrebollo.basearch.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.jrebollo.basearch.DependencyInjector
import com.jrebollo.basearch.databinding.ViewUserDetailBinding
import com.jrebollo.basearch.ui.base.BaseView
import com.jrebollo.basearch.ui.base.ErrorType
import com.jrebollo.basearch.ui.viewmodel.UserDetailVM
import com.jrebollo.basearch.ui.viewmodel.UserDetailVMFactory


class UserDetailView : BaseView<ViewUserDetailBinding, UserDetailVM, UserDetailVMFactory>() {

    override val viewModel: UserDetailVM by viewModels {
        buildViewModelFactory()
    }

    override fun buildViewModelFactory(): UserDetailVMFactory {
        return DependencyInjector.provideUserDetailVMFactory()
    }

    override fun initComponents(binding: ViewUserDetailBinding) {
    }

    override fun addListeners(binding: ViewUserDetailBinding) {
    }

    override fun addObservers(binding: ViewUserDetailBinding) {
    }

    override fun errorHandler(errorType: ErrorType) {

    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ViewUserDetailBinding = ViewUserDetailBinding.inflate(inflater, container, false)
}