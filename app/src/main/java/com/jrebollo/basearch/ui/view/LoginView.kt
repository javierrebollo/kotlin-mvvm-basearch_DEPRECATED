package com.jrebollo.basearch.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.jrebollo.basearch.DependencyInjector
import com.jrebollo.basearch.databinding.ViewLoginBinding
import com.jrebollo.basearch.ui.base.BaseView
import com.jrebollo.basearch.ui.base.ErrorType
import com.jrebollo.basearch.ui.viewmodel.LoginVM
import com.jrebollo.basearch.ui.viewmodel.LoginVMFactory

class LoginView : BaseView<ViewLoginBinding, LoginVM, LoginVMFactory>() {
    override val viewModel: LoginVM by viewModels {
        buildViewModelFactory()
    }

    override fun buildViewModelFactory(): LoginVMFactory {
        return DependencyInjector.provideLoginVMFactory()
    }

    override fun addListeners(binding: ViewLoginBinding) {
        binding.etUsername.addTextChangedListener {
            viewModel.usernameLiveData.value = it?.toString()
        }
        binding.etPassword.addTextChangedListener {
            viewModel.passwordLiveData.value = it?.toString()
        }
        binding.btnLogin.setOnClickListener { viewModel.login() }
    }

    override fun initComponents(binding: ViewLoginBinding) {
        binding.btnLogin.isEnabled = viewModel.enableLoginButton.value ?: false
    }

    override fun addObservers(binding: ViewLoginBinding) {
        viewModel.enableLoginButton.observe(viewLifecycleOwner) { result ->
            binding.btnLogin.isEnabled = result
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ViewLoginBinding = ViewLoginBinding.inflate(inflater, container, false)

    override fun errorHandler(errorType: ErrorType) {
        when (errorType) {
            is ErrorType.LoginError -> Toast.makeText(context, errorType.message, Toast.LENGTH_LONG).show()
        }
    }
}
