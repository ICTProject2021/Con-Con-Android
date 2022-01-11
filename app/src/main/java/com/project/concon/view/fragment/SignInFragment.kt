package com.project.concon.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.project.concon.R
import com.project.concon.view.base.BaseVMFragment
import com.project.concon.databinding.FragmentSignInBinding
import com.project.concon.utils.MessageUtils
import com.project.concon.utils.PreferenceUtils
import com.project.concon.view.activity.MainActivity
import com.project.concon.view.viewmodel.SignInViewModel

class SignInFragment : BaseVMFragment<FragmentSignInBinding, SignInViewModel>() {

    override fun setBinding() {
        binding.vm = viewModel
    }

    override fun getLayoutRes(): Int = R.layout.fragment_sign_in

    override fun getViewModelClass(): Class<SignInViewModel> = SignInViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()

        binding.motionLayoutSignIn.transitionToStart()
        binding.motionLayoutSignIn.transitionToEnd()

        binding.fabCloseSignIn.setOnClickListener {
            navigateToIntro()
        }
    }

    private fun observe() = with(viewModel) {
        id.observe(viewLifecycleOwner) {
            idErr.value = when(it.isNullOrBlank()) {
                true -> getString(R.string.error_id)
                false -> ""
            }
        }

        pw.observe(viewLifecycleOwner) {
            pwErr.value = when(it.isNullOrBlank()) {
                true -> getString(R.string.error_pw)
                false -> ""
            }
        }

        isSuccess.observe(viewLifecycleOwner) {
            PreferenceUtils.token = it
            navigateToMain()
        }

        isFailure.observe(viewLifecycleOwner) {
            MessageUtils.showFailDialog(requireActivity(),
                "${getString(R.string.error_sign_in)}\n($it)")
        }

        isLoading.observe(viewLifecycleOwner) {
            if (it) {
                MessageUtils.showProgress(requireActivity())
            } else {
                MessageUtils.dismissProgress()
            }
        }
    }

    private fun navigateToIntro() {
        navController.navigate(R.id.action_signInFragment_to_introFragment)
    }

    private fun navigateToMain() {
        startActivity(Intent(requireActivity(), MainActivity::class.java))
    }
}