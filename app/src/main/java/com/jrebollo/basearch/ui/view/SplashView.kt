package com.jrebollo.basearch.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.jrebollo.basearch.AndroidDependencyInjector
import com.jrebollo.basearch.databinding.ViewSplashBinding
import com.jrebollo.basearch.ui.base.BaseView
import com.jrebollo.basearch.ui.viewmodel.SplashVM
import com.jrebollo.basearch.ui.viewmodel.SplashVMFactory


class SplashView : BaseView<SplashVM, SplashVMFactory>() {
    private lateinit var binding: ViewSplashBinding
    override val viewModel: SplashVM by viewModels {
        buildViewModelFactory()
    }

    override fun buildViewModelFactory(): SplashVMFactory {
        return AndroidDependencyInjector.provideSplashVMFactory()
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ViewSplashBinding.inflate(inflater, container, false)

        return binding.root
    }


}
