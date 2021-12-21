package com.project.concon.base

import androidx.fragment.app.Fragment

class BaseFragment<B> : Fragment() {
    protected var _binding: B? = null

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}