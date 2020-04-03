package com.jrebollo.basearch.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.jrebollo.basearch.AndroidDependencyInjector
import com.jrebollo.basearch.databinding.ViewLoginBinding
import com.jrebollo.basearch.ui.base.BaseView
import com.jrebollo.basearch.ui.viewmodel.LoginVM
import com.jrebollo.basearch.ui.viewmodel.LoginVMFactory

class LoginView : BaseView<LoginVM, LoginVMFactory>() {
    private lateinit var binding: ViewLoginBinding

    override val viewModel: LoginVM by viewModels {
        buildViewModelFactory()
    }

    override fun buildViewModelFactory(): LoginVMFactory {
        return AndroidDependencyInjector.provideLoginVMFactory()
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ViewLoginBinding.inflate(inflater, container, false)

        return binding.root
    }
}
