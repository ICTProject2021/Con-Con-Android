package com.example.a2021ictproject.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.a2021ictproject.R
import com.example.a2021ictproject.activity.MainActivity
import com.example.a2021ictproject.databinding.SignUpFragmentBinding
import com.example.a2021ictproject.network.dto.request.SignUpRequest
import com.example.a2021ictproject.utils.PreferenceUtils
import com.example.a2021ictproject.viewmodel.SignUpViewModel
import com.google.android.material.internal.TextWatcherAdapter

class SignUpFragment : Fragment() {

    private val navController: NavController by lazy { findNavController() }

    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var binding: SignUpFragmentBinding

    /* 시간 남으면 모션 레이아웃 구현할게용 */
    private val motionLayout: MotionLayout by lazy { binding.motionLayout }

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

        motionLayout.transitionToStart()
        observe()

        /* onClick */
        binding.fabCloseSignUp.setOnClickListener {
            navigateToIntro()
        }

        binding.etPasswordSignUp.addTextChangedListener {
            binding.etLayoutPasswordSignUp.error = when {
                it.isNullOrEmpty() -> "비밀번호를 입력해주세요."
                else -> null
            }
        }

        binding.etPhoneNumberSignUp.addTextChangedListener {
            binding.etLayoutPhoneNumberSignUp.error = when {
                it.isNullOrEmpty() -> "휴대폰 번호를 입력해주세요."
                else -> null
            }
        }

        binding.etNicknameSignUp.addTextChangedListener {
            binding.etLayoutNicknameSignUp.error = when {
                it.isNullOrEmpty() -> "닉네임을 입력해주세요."
                else -> null
            }
        }
    }

    private fun observe() = with(viewModel) {
        id.observe(viewLifecycleOwner) {
            idErr.value = when(it.isEmpty()) {
                true -> "아이디를 입력해주세요."
                false -> ""
            }
            signUpBtnEnabled()
        }

        password.observe(viewLifecycleOwner) {
            pwErr.value = when(it.isEmpty()) {
                true -> "비밀번호를 입력해주세요."
                false -> ""
            }
            signUpBtnEnabled()
        }

        phoneNumber.observe(viewLifecycleOwner) {
            phoneErr.value = when(it.isEmpty()) {
                true -> "휴대폰 번호를 입력해주세요."
                false -> ""
            }
            signUpBtnEnabled()
        }

        nickname.observe(viewLifecycleOwner) {
            nicknameErr.value = when(it.isEmpty()) {
                true -> "닉네임을 입력해주세요."
                false -> ""
            }
            signUpBtnEnabled()
        }

        postCheckIdRes.observe(viewLifecycleOwner) {
            when (it) {
                null ->
                    Toast.makeText(requireContext(), getString(R.string.fail_server), Toast.LENGTH_SHORT).show()

                "available" -> idCheck.value = true

                "exist" ->
                    Toast.makeText(requireContext(), "중복된 아이디입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        postSignUpRes.observe(viewLifecycleOwner) {
            when (it) {
                null ->
                    Toast.makeText(requireContext(), getString(R.string.fail_server), Toast.LENGTH_SHORT).show()

                else -> {
                    PreferenceUtils.token = it
                    navigateToMain()
                }
            }
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(requireActivity(), MainActivity::class.java))
    }

    private fun navigateToIntro() {
        navController.navigate(R.id.action_signUpFragment_to_introFragment)
    }
}