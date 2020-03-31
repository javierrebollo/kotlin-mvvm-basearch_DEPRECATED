package com.jrebollo.basearch.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseView<VM : BaseViewModel, VMF : BaseViewModelFactory<VM>> : Fragment() {
    val TAG: String = this.javaClass.simpleName
    abstract val viewModel: VM
    abstract fun buildViewModelFactory(): VMF
    abstract fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initBinding(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.start()
    }

}