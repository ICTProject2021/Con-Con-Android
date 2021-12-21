package com.project.concon.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.concon.viewmodel.factory.ViewModelFactory
import javax.inject.Inject

abstract class BaseVMFragment<B: ViewDataBinding, VM: ViewModel> : BaseFragment<B>() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    protected lateinit var viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        // todo owner 를 this 로 해도될지 테스트..
        viewModel = ViewModelProvider(this, viewModelFactory)[viewModel.javaClass]
        setBinding()
        return binding.root
    }

    abstract fun setBinding()
}