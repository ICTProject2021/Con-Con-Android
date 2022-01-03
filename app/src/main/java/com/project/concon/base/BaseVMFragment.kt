package com.project.concon.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseVMFragment<B: ViewDataBinding, VM: ViewModel> : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    protected lateinit var viewModel: VM

    private var _binding: B? = null
    protected val binding get() = _binding!!

    protected val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        setViewModelOrBinding(viewModelFactory)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * 파라미터로 주어진 viewModelFactory로 ViewModel 초기화, 데이터 바인딩 (강제 x)
     * */
    abstract fun setViewModelOrBinding(viewModelFactory: ViewModelProvider.Factory)

    /** 상속받는 프래그먼트의 layout 리소스를 반환한다 */
    @LayoutRes
    abstract fun getLayoutRes(): Int
}