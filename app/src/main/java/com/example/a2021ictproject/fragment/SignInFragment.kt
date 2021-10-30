package com.example.a2021ictproject.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.a2021ictproject.R
import com.example.a2021ictproject.activity.MainActivity
import com.example.a2021ictproject.databinding.SignInFragmentBinding
import com.example.a2021ictproject.network.dto.request.SignInRequest
import com.example.a2021ictproject.utils.PreferenceUtils
import com.example.a2021ictproject.viewmodel.SignInViewModel
import com.google.android.material.internal.TextWatcherAdapter

class SignInFragment : Fragment() {

    private val navController: NavController by lazy {
        findNavController()
    }

    private lateinit var binding: SignInFragmentBinding
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.sign_in_fragment, container, false)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

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
                true -> getString(R.string.error_input_id)
                false -> ""
            }
        }

        pw.observe(viewLifecycleOwner) {
            pwErr.value = when(it.isNullOrBlank()) {
                true -> getString(R.string.error_input_pw)
                false -> ""
            }
        }

        postSignInRes.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it.msg) {
                    "fail" -> Toast.makeText(requireContext(), "올바른 아이디, 비밀번호가 아님", Toast.LENGTH_SHORT).show()

                    else -> {
                        PreferenceUtils.token = it.msg
                        navigateToMain()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "서버 통신 실패", Toast.LENGTH_SHORT).show()
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