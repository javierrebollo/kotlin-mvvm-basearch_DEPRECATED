package com.jrebollo.basearch.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.jrebollo.basearch.utils.NavigationCommand

abstract class BaseView<DB : ViewDataBinding, VM : BaseViewModel, VMF : BaseViewModelFactory<VM>> : Fragment() {
    protected val TAG: String = this::class.java.simpleName

    protected lateinit var binding: DB
    abstract val viewModel: VM
    abstract fun buildViewModelFactory(): VMF
    abstract fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): DB

    abstract fun initComponents(binding: DB)
    abstract fun addListeners(binding: DB)
    abstract fun addObservers(binding: DB)
    abstract fun errorHandler(errorType: ErrorType)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = initBinding(inflater, container, savedInstanceState)
        initComponents(binding)
        addListeners(binding)
        addObservers(binding)
        viewModel.errorNotifier.observe(viewLifecycleOwner, this::errorHandler)
        return binding.root
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
}