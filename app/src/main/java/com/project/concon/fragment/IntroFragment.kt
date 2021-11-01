package com.project.concon.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.project.concon.R
import com.project.concon.databinding.IntroFragmentBinding

class IntroFragment : Fragment() {

    private val navController: NavController by lazy {
        findNavController()
    }

    private lateinit var binding: IntroFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = IntroFragmentBinding.inflate(inflater, container, false)
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