package com.project.concon.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.project.concon.R
import com.project.concon.utils.MessageUtils
import com.project.concon.utils.PreferenceUtils
import com.project.concon.viewmodel.SignUpViewModel
import com.project.concon.databinding.SignUpFragmentBinding

class SignUpFragment : Fragment() {

    private val navController: NavController by lazy { findNavController() }

    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var binding: SignUpFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.sign_up_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()

        binding.motionLayoutSignUp.transitionToStart()
        binding.motionLayoutSignUp.transitionToEnd()

        /* onClick */
        binding.fabCloseSignUp.setOnClickListener {
            navigateToIntro()
        }
    }

    private fun observe() = with(viewModel) {
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

        postCheckIdRes.observe(viewLifecycleOwner) {
            when (it?.msg) {
                null -> MessageUtils.showFailDialog(requireActivity(), getString(R.string.fail_server))

                "available" -> idCheck.value = true

                "exist"  -> MessageUtils.showToast(requireContext(), "중복된 아이디입니다.")
            }
        }

        postSignUpRes.observe(viewLifecycleOwner) {
            when (it) {
                null -> MessageUtils.showFailDialog(requireActivity(), getString(R.string.fail_server))

                else -> {
                    PreferenceUtils.token = it.msg
                    navigateToMain()
                }
            }
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(requireActivity(), com.project.concon.activity.MainActivity::class.java))
    }

    private fun navigateToIntro() {
        navController.navigate(R.id.action_signUpFragment_to_introFragment)
    }
}