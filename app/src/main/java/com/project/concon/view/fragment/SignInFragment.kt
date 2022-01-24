package com.project.concon.view.fragment

import com.project.concon.R
import com.project.concon.base.BaseFragment
import com.project.concon.databinding.FragmentSignInBinding
import com.project.concon.view.activity.MainActivity
import com.project.concon.viewmodel.SignInViewModel
import com.project.concon.widget.extension.startActivityWithFinish
import com.project.concon.widget.utils.MessageUtils
import com.project.concon.widget.utils.PreferenceUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : BaseFragment<FragmentSignInBinding, SignInViewModel>() {

    override val viewModel: SignInViewModel by viewModel()

    override fun init() {
        binding.motionLayoutSignIn.transitionToStart()
        binding.motionLayoutSignIn.transitionToEnd()

        binding.fabClose.setOnClickListener {
            navController.popBackStack()
        }
    }

    override fun observerViewModel() {
        with (viewModel) {
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
                requireActivity().startActivityWithFinish(requireContext(), MainActivity::class.java)
            }

            isFailure.observe(viewLifecycleOwner) {
                MessageUtils.showFailDialog(requireActivity(), "${getString(R.string.error_sign_in)}\n($it)")
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