package com.project.concon.view.fragment

import androidx.fragment.app.viewModels
import com.project.concon.base.BaseFragment
import com.project.concon.databinding.FragmentIntroBinding
import com.project.concon.viewmodel.IntroViewModel
import com.project.concon.widget.extension.safeNavigate

class IntroFragment : BaseFragment<FragmentIntroBinding, IntroViewModel>() {
    override val viewModel: IntroViewModel by viewModels()

    override fun init() {}

    override fun observerViewModel() {
        with(viewModel) {
            onSignInEvent.observe(this@IntroFragment) {
                navController.safeNavigate(IntroFragmentDirections.toSignInFragment())
            }

            onSignUpEvent.observe(this@IntroFragment) {
                navController.safeNavigate(IntroFragmentDirections.toSignUpFragment())
            }
        }
    }
}