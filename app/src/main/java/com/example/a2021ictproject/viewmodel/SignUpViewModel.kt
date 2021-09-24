package com.example.a2021ictproject.viewmodel

import androidx.lifecycle.ViewModel
import com.example.a2021ictproject.network.`object`.RetrofitInstance
import com.example.a2021ictproject.network.dto.request.SignUpRequest
import java.util.function.BiPredicate

class SignUpViewModel : ViewModel() {

    private val accountService by lazy { RetrofitInstance.accountService }

    fun postSignUp(signUpRequest: SignUpRequest) {

    }
}