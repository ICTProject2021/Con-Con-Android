package com.example.a2021ictproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a2021ictproject.network.`object`.RetrofitInstance
import com.example.a2021ictproject.network.dao.AccountService
import com.example.a2021ictproject.network.dto.request.SignInRequest

class SignInViewModel : ViewModel() {

    private val accountService: AccountService by lazy { RetrofitInstance.accountService }

    val token = MutableLiveData<String>()

    fun signIn(signIn: SignInRequest) {

    }

    fun isValidId(id: String): String? {
        if (id.isEmpty()) {
            return "아이디를 입력해주세요."
        }

        return null
    }

    fun isValidPassword(password: String): String? {
        if (password.isEmpty()) {
            return "비밀번호를 입력해주세요."
        }
        return null
    }

}