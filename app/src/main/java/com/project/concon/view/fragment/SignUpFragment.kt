package com.project.concon.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.project.concon.R
import com.project.concon.base.BaseVMFragment
import com.project.concon.databinding.FragmentSignUpBinding
import com.project.concon.utils.MessageUtils
import com.project.concon.utils.PreferenceUtils
import com.project.concon.view.activity.MainActivity
import com.project.concon.viewmodel.SignUpViewModel

class SignUpFragment : BaseVMFragment<FragmentSignUpBinding, SignUpViewModel>() {

    override fun getLayoutRes(): Int = R.layout.fragment_sign_up

    override fun setBinding() {
        binding.vm = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()

        binding.motionLayoutSignUp.transitionToStart()
        binding.motionLayoutSignUp.transitionToEnd()

        binding.fabCloseSignUp.setOnClickListener {
            navigateToIntro()
        }
    }

    private fun observe() = with(viewModel) {
        // todo 존나 중복돼 보이는 signUpBtnEnabled 없애기
        id.observe(viewLifecycleOwner) {
            idErr.value = when(it.isBlank()) {
                true -> getString(R.string.error_input_id)
                false -> ""
            }
            signUpBtnEnabled()
        }

        password.observe(viewLifecycleOwner) {
            pwErr.value = when(it.isBlank()) {
                true -> getString(R.string.error_input_pw)
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
            navigateToMain()
        }

        isFailure.observe(viewLifecycleOwner) {
            MessageUtils.showFailDialog(requireActivity(), it)
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(requireActivity(), MainActivity::class.java))
    }

    private fun navigateToIntro() {
        navController.navigate(R.id.action_signUpFragment_to_introFragment)
    }
}