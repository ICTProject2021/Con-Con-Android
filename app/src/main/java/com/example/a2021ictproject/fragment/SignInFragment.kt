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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()

        /* OnClick */
        binding.fabCloseSignIn.setOnClickListener {
            navigateToIntro()
        }

        /* 에러가 없을 시 로그인 후... */
        binding.btnSignIn.setOnClickListener {
            val id = binding.etIdSignIn.text.toString().trim()
            val password = binding.etPasswordSignIn.text.toString().trim()

            if (id.isBlank() || password.isBlank()) {
                Toast.makeText(requireContext(), "빈칸을 입력해주세요.", Toast.LENGTH_LONG).show()
            } else {
                viewModel.postSignIn(getSignIn())
            }
        }

        /* TextWatcher */
        binding.etIdSignIn.addTextChangedListener {
            binding.etLayoutIdSignIn.error = when {
                it.isNullOrBlank() -> "아이디를 입력해주세요."
                else -> null
            }
        }

        binding.etPasswordSignIn.addTextChangedListener {
            binding.etLayoutPasswordSignIn.error = when {
                it.isNullOrBlank() -> "비밀번호를 입력해주세요."
                else -> null
            }
        }

    }

    private fun getSignIn(): SignInRequest =
        SignInRequest(
            binding.etIdSignIn.text.toString(),
            binding.etPasswordSignIn.text.toString()
        )

    private fun observe() {
        viewModel.postSignInRes.observe(viewLifecycleOwner, {
            
        })
    }

    private fun navigateToIntro() {
        navController.navigate(R.id.action_signInFragment_to_introFragment)
    }

    private fun navigateToMain() {
        startActivity(Intent(requireActivity(), MainActivity::class.java))
    }
}