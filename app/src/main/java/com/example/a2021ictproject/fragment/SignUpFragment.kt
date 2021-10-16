package com.example.a2021ictproject.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.a2021ictproject.R
import com.example.a2021ictproject.activity.MainActivity
import com.example.a2021ictproject.databinding.SignUpFragmentBinding
import com.example.a2021ictproject.utils.PreferenceUtils
import com.example.a2021ictproject.viewmodel.SignUpViewModel

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
    }

    private fun observe() = with(viewModel) {
        id.observe(viewLifecycleOwner) {
            idErr.value = when(it.isEmpty()) {
                true -> getString(R.string.error_input_id)
                false -> ""
            }
            signUpBtnEnabled()
        }

        password.observe(viewLifecycleOwner) {
            pwErr.value = when(it.isEmpty()) {
                true -> getString(R.string.error_input_pw)
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