package com.jrebollo.basearch.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.jrebollo.basearch.AndroidDependencyInjector
import com.jrebollo.basearch.databinding.ViewHomeBinding
import com.jrebollo.basearch.ui.base.BaseView
import com.jrebollo.basearch.ui.viewmodel.HomeVM
import com.jrebollo.basearch.ui.viewmodel.HomeVMFactory


class HomeView : BaseView<HomeVM, HomeVMFactory>() {
    private lateinit var binding: ViewHomeBinding

    override val viewModel: HomeVM by viewModels {
        buildViewModelFactory()
    }

    override fun buildViewModelFactory(): HomeVMFactory {
        return AndroidDependencyInjector.provideHomeVMFactory()
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ViewHomeBinding.inflate(inflater, container, false)

        return binding.root
    }
}
