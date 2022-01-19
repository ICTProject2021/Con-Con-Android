package com.project.concon.view.fragment

import com.project.concon.base.BaseFragment
import com.project.concon.databinding.FragmentIntroBinding
import com.project.concon.viewmodel.IntroViewModel

class IntroFragment : BaseFragment<FragmentIntroBinding, IntroViewModel>() {

    override fun getViewModelClass(): Class<IntroViewModel> = IntroViewModel::class.java

    override fun init() {}

    override fun observerViewModel() {
        with(viewModel) {
            onSignInEvent.observe(this@IntroFragment) {
                navController.navigate(IntroFragmentDirections.toSignInFragment())
            }

            onSignUpEvent.observe(this@IntroFragment) {
                navController.navigate(IntroFragmentDirections.toSignUpFragment())
            }
        }
    }
}