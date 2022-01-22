package com.project.concon.view.fragment

import android.content.Intent
import com.project.concon.R
import com.project.concon.base.BaseFragment
import com.project.concon.databinding.FragmentSignUpBinding
import com.project.concon.view.activity.MainActivity
import com.project.concon.viewmodel.SignUpViewModel
import com.project.concon.widget.utils.MessageUtils
import com.project.concon.widget.utils.PreferenceUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : BaseFragment<FragmentSignUpBinding, SignUpViewModel>() {

//    override fun getViewModelClass(): Class<SignUpViewModel> = SignUpViewModel::class.java
    private val viewModel: SignUpViewModel by viewModel()

    override fun init() {
        binding.motionLayoutSignUp.transitionToStart()
        binding.motionLayoutSignUp.transitionToEnd()
    }

    override fun observerViewModel() {
        with(viewModel) {
            onClose.observe(this@SignUpFragment) {
                navController.popBackStack()
            }

            id.observe(viewLifecycleOwner) {
                idErr.value = when(it.isBlank()) {
                    true -> getString(R.string.error_id)
                    false -> ""
                }
                signUpBtnEnabled()
            }

            password.observe(viewLifecycleOwner) {
                pwErr.value = when(it.isBlank()) {
                    true -> getString(R.string.error_pw)
                    false -> ""
                }
                signUpBtnEnabled()
            }

            phoneNumber.observe(viewLifecycleOwner) {
                phoneErr.value = when(it.isBlank()) {
                    true -> "휴대폰 번호를 입력해주세요."
                    false -> ""
                }
                signUpBtnEnabled()
            }

            nickname.observe(viewLifecycleOwner) {
                nicknameErr.value = when(it.isBlank()) {
                    true -> "닉네임을 입력해주세요."
                    false -> ""
                }
                signUpBtnEnabled()
            }

            isSuccessCheckId.observe(viewLifecycleOwner) {
                idCheck.value = true
            }

            isSuccessSignUp.observe(viewLifecycleOwner) {
                PreferenceUtils.token = it
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            }

            isFailure.observe(viewLifecycleOwner) {
                MessageUtils.showFailDialog(requireActivity(), it)
            }
        }
    }
}