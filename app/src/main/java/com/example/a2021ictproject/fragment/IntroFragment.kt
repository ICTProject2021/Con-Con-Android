package com.example.a2021ictproject.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.a2021ictproject.R
import com.example.a2021ictproject.databinding.IntroFragmentBinding

class IntroFragment : Fragment() {

    private val navController: NavController by lazy {
        findNavController()
    }

    private lateinit var binding: IntroFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.intro_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignInIntro.setOnClickListener {
            navigateToSignIn()
        }

        binding.btnSignUpIntro.setOnClickListener {
            navigateToSignUp()
        }
    }

    private fun navigateToSignIn() {
        navController.navigate(R.id.action_introFragment_to_signInFragment)
    }

    private fun navigateToSignUp() {
        navController.navigate(R.id.action_introFragment_to_signUpFragment)
    }
}