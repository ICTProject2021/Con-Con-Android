package com.project.concon.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.project.concon.BR
import com.project.concon.R
import dagger.android.support.DaggerFragment
import java.lang.StringBuilder
import java.lang.reflect.ParameterizedType
import java.util.*
import javax.inject.Inject

abstract class BaseFragment<VB: ViewDataBinding, VM: ViewModel> : Fragment() {

//    @Inject
//    lateinit var viewModelFactory: ViewModelProvider.Factory
//    @Inject
//    lateinit var mViewModel: VM
//
//    protected val viewModel: VM get() = mViewModel
    protected lateinit var binding: VB

    protected val navController by lazy {
        findNavController()
    }

//    protected abstract fun getViewModelClass(): Class<VM>

    protected abstract fun init()

    protected abstract fun observerViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        performDataBinding(inflater, container)
        observerViewModel()
        init()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }

    private fun performDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
//        viewModel = ViewModelProvider(this, viewModelFactory?: defaultViewModelProviderFactory)[getViewModelClass()]

        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        binding.lifecycleOwner = this
//        binding.setVariable(BR.vm, viewModel)
        binding.executePendingBindings()
    }

    @LayoutRes
    private fun getLayoutRes(): Int {
        val split = ((Objects.requireNonNull(javaClass.genericSuperclass) as ParameterizedType).actualTypeArguments[0] as Class<*>)
            .simpleName.replace("Binding$".toRegex(), "")
            .split("(?<=.)(?=\\p{Upper})".toRegex())
            .dropLastWhile { it.isEmpty() }.toTypedArray()

        val name = StringBuilder()
        for (i in split.indices) {
            name.append(split[i].lowercase(Locale.ROOT))
            if (i != split.size - 1) name.append("_")
        }

        return R.layout::class.java.getField(name.toString()).getInt(R.layout::class.java)
    }
}