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
import androidx.core.widget.addTextChangedListener
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
                Toast.makeText(requireContext(), "빈칸을 입력해주세요.", Toast.LENGTH_LONG).show()
            } else {
                viewModel.postSignUp(getSignUp())
            }
        }

        /* 공백 체크 */
        binding.etIdSignUp.addTextChangedListener {
            binding.etLayoutIdSignUp.error = when {
                it.isNullOrEmpty() -> "아이디를 입력해주세요."
                else -> null
            }
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

    private fun observe() {
        viewModel.postSignUpRes.observe(viewLifecycleOwner, {
            when (it?.code) {
                null ->
                    Toast.makeText(requireContext(), getString(R.string.fail_server), Toast.LENGTH_SHORT).show()
                in 200..299 -> {
                    navigateToMain()
                }
                else ->
                    Toast.makeText(requireContext(), "회원가입에 실패했습니다.Z", Toast.LENGTH_SHORT).show()
            }
        })
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