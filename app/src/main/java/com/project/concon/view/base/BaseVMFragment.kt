package com.project.concon.view.base

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
        viewModel = ViewModelProvider(this, viewModelFactory)[getViewModelClass()]
        setBinding()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /** 데이터 바인딩 설정 (설정할 변수가 없으면 빈 함수로 두기) */
    abstract fun setBinding()

    /** 상속받는 프래그먼트의 layout 리소스를 반환 */
    @LayoutRes
    abstract fun getLayoutRes(): Int

    /** viewModel 클래스 반환 */
    abstract fun getViewModelClass(): Class<VM>
}