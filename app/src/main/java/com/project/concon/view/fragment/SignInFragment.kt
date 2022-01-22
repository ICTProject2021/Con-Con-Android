package com.project.concon.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.project.concon.R
import com.project.concon.base.BaseFragment
import com.project.concon.databinding.FragmentSignInBinding
import com.project.concon.widget.utils.MessageUtils
import com.project.concon.widget.utils.PreferenceUtils
import com.project.concon.view.activity.MainActivity
import com.project.concon.viewmodel.SignInViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : BaseFragment<FragmentSignInBinding, SignInViewModel>() {

    override val viewModel: SignInViewModel by viewModel()

    override fun init() {
        binding.motionLayoutSignIn.transitionToStart()
        binding.motionLayoutSignIn.transitionToEnd()
    }

    override fun observerViewModel() {
        with (viewModel) {
            onClose.observe(this@SignInFragment) {
                navController.popBackStack()
            }

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
                startActivity(Intent(requireActivity(), MainActivity::class.java))
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
    }
}