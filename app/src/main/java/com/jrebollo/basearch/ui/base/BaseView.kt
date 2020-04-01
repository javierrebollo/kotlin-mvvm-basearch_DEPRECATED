package com.jrebollo.basearch.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jrebollo.basearch.utils.NavigationCommand

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.navigation.observe(this, Observer<NavigationCommand> { command ->
            when (command) {
                is NavigationCommand.To ->
                    findNavController().navigate(command.directions)
                is NavigationCommand.BackTo ->
                    findNavController().navigate(command.destinationId)
                is NavigationCommand.Back ->
                    findNavController().popBackStack()
                is NavigationCommand.ToRoot ->
                    TODO()
                is NavigationCommand.WithArgs ->
                    TODO()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.start()
    }

}