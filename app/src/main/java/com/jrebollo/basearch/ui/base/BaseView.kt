package com.jrebollo.basearch.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.jrebollo.basearch.ui.view.dialog.LoadingDialog
import com.jrebollo.basearch.utils.NavigationCommand

abstract class BaseView<DB : ViewDataBinding, VM : BaseViewModel, VMF : BaseViewModelFactory<VM>> : Fragment() {
    protected val TAG: String = this::class.java.simpleName

    private val loadingDialog: LoadingDialog = LoadingDialog()
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
        viewModel.loadingState.observe(viewLifecycleOwner) {
            if (it) {
                loadingDialog.show(parentFragmentManager, TAG)
            } else {
                loadingDialog.dismiss()
            }
        }
        viewModel.forceKeyboardState.observe(viewLifecycleOwner) { keyboardState ->
            keyboardState?.let { safeKeyboardState ->
                when (safeKeyboardState) {
                    KeyboardState.SHOW -> openKeyboard()
                    KeyboardState.HIDE -> closeKeyboard()
                }
            }
        }

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

        viewModel.loadData()
    }

    private fun closeKeyboard() {
        activity?.let { safeActivity ->
            val imm: InputMethodManager =
                safeActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        }
    }

    private fun openKeyboard() {
        activity?.let { safeActivity ->
            val imm: InputMethodManager =
                safeActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
        }
    }
}