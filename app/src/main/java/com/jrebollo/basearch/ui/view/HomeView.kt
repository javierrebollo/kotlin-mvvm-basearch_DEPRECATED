package com.jrebollo.basearch.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jrebollo.basearch.DependencyInjector
import com.jrebollo.basearch.databinding.ViewHomeBinding
import com.jrebollo.basearch.ui.adapter.UserListAdapter
import com.jrebollo.basearch.ui.base.BaseView
import com.jrebollo.basearch.ui.base.ErrorType
import com.jrebollo.basearch.ui.viewmodel.HomeVM
import com.jrebollo.basearch.ui.viewmodel.HomeVMFactory


class HomeView : BaseView<ViewHomeBinding, HomeVM, HomeVMFactory>() {

    private lateinit var rvUserList: RecyclerView

    override val viewModel: HomeVM by viewModels {
        buildViewModelFactory()
    }

    override fun buildViewModelFactory(): HomeVMFactory {
        return DependencyInjector.provideHomeVMFactory()
    }

    override fun initComponents(binding: ViewHomeBinding) {
        rvUserList = binding.rvUserList
        rvUserList.layoutManager = LinearLayoutManager(context)
        rvUserList.adapter = UserListAdapter().apply {
            onItemClick = {
                viewModel.openUserDetails(it)
            }
        }
    }

    override fun addListeners(binding: ViewHomeBinding) {
        binding.btnRefresh.setOnClickListener {
            viewModel.refreshUsers()
        }
    }

    override fun addObservers(binding: ViewHomeBinding) {
        viewModel.liveDataUsers?.observe(viewLifecycleOwner) {
            (binding.rvUserList.adapter as UserListAdapter).updateItems(it)
        }
    }

    override fun errorHandler(errorType: ErrorType) {

    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ViewHomeBinding = ViewHomeBinding.inflate(inflater, container, false)
}
