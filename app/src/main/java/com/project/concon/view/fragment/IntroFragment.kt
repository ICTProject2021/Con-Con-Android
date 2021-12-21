package com.project.concon.view.fragment

import android.os.Bundle
import android.view.View
import com.project.concon.R
import com.project.concon.base.BaseFragment
import com.project.concon.databinding.FragmentIntroBinding

class IntroFragment : BaseFragment<FragmentIntroBinding>() {

    override fun getLayoutRes(): Int = R.layout.fragment_intro

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignInIntro.setOnClickListener {
            navigateToSignIn()
        }

        binding.btnSignUpIntro.setOnClickListener {
            navigateToSignUp()
        }
    }

    private fun navigateToSignIn() {
        navController.navigate(R.id.action_introFragment_to_signInFragment)
    }

    private fun navigateToSignUp() {
        navController.navigate(R.id.action_introFragment_to_signUpFragment)
    }
}