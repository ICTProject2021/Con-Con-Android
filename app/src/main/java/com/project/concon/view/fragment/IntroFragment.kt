package com.project.concon.view.fragment

import androidx.fragment.app.viewModels
import com.project.concon.base.BaseFragment
import com.project.concon.databinding.FragmentIntroBinding
import com.project.concon.viewmodel.IntroViewModel
import com.project.concon.widget.extension.safeNavigate

class IntroFragment : BaseFragment<FragmentIntroBinding, IntroViewModel>() {
    override val viewModel: IntroViewModel by viewModels()

    override fun init() {
        binding.btnSignInIntro.setOnClickListener {
            navController.safeNavigate(IntroFragmentDirections.toSignInFragment())
        }

        binding.btnSignUpIntro.setOnClickListener {
            navController.safeNavigate(IntroFragmentDirections.toSignUpFragment())
        }
    }

    override fun observerViewModel() {}
}