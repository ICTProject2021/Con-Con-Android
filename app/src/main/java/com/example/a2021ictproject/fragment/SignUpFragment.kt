package com.example.a2021ictproject.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.a2021ictproject.R
import com.example.a2021ictproject.activity.MainActivity
import com.example.a2021ictproject.databinding.SignUpFragmentBinding
import com.example.a2021ictproject.network.dto.request.SignUpRequest
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

        binding.btnSignUp.setOnClickListener {
            val id = binding.etIdSignUp.text.toString().trim()
            val password = binding.etPasswordSignUp.text.toString().trim()
            val phoneNumber = binding.etPhoneNumberSignUp.text.toString().trim()
            val nickname = binding.etNicknameSignUp.text.toString().trim()

            if (id.isBlank() || password.isBlank() || phoneNumber.isBlank() || nickname.isBlank()) {
                Toast.makeText(requireContext(), "정보를 다시 입력해주세요.", Toast.LENGTH_LONG).show()
            } else {
                viewModel.postSignUp(getSignUp())
            }
        }

        /* TextWatcher - 공백 체크 */
        binding.etIdSignUp.addTextChangedListener(object : TextWatcherAdapter() {
            override fun afterTextChanged(s: Editable) {
                when {
                    binding.etIdSignUp.text.isNullOrEmpty() ->
                        binding.etLayoutIdSignUp.error = "아이디를 입력해주세요."

                    else -> {
                        binding.etLayoutIdSignUp.error = null
                    }
                }
            }
        })

        binding.etPasswordSignUp.addTextChangedListener(object : TextWatcherAdapter() {
            override fun afterTextChanged(s: Editable) {
                when {
                    binding.etPasswordSignUp.text.isNullOrEmpty() ->
                        binding.etLayoutPasswordSignUp.error = "비밀번호를 입력해주세요."

                    else -> {
                        binding.etLayoutPasswordSignUp.error = null
                    }
                }
            }
        })

        binding.etPhoneNumberSignUp.addTextChangedListener(object : TextWatcherAdapter() {
            override fun afterTextChanged(s: Editable) {
                when {
                    binding.etPhoneNumberSignUp.text.isNullOrEmpty() ->
                        binding.etLayoutPhoneNumberSignUp.error = "휴대폰 번호를 입력해주세요."

                    else -> {
                        binding.etLayoutPhoneNumberSignUp.error = null
                    }
                }
            }
        })

        binding.etNicknameSignUp.addTextChangedListener(object : TextWatcherAdapter() {
            override fun afterTextChanged(s: Editable) {
                when {
                    binding.etNicknameSignUp.text.isNullOrEmpty() ->
                        binding.etLayoutNicknameSignUp.error = "닉네임을 입력해주세요."

                    else -> {
                        binding.etLayoutNicknameSignUp.error = null
                    }
                }
            }
        })
    }

    private fun observe() {

    }

    private fun getSignUp(): SignUpRequest =
        SignUpRequest(
            binding.etIdSignUp.toString(),
            binding.etPasswordSignUp.toString(),
            binding.etPhoneNumberSignUp.toString(),
            binding.etNicknameSignUp.toString()
        )

    private fun navigateToMain() {
        startActivity(Intent(requireActivity(), MainActivity::class.java))
    }

    private fun navigateToIntro() {
        navController.navigate(R.id.action_signUpFragment_to_introFragment)
    }
}