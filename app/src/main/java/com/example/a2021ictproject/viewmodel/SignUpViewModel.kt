package com.example.a2021ictproject.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a2021ictproject.network.`object`.RetrofitInstance
import com.example.a2021ictproject.network.dto.request.SignUpRequest
import com.example.a2021ictproject.network.dto.response.Res
import com.example.a2021ictproject.network.dto.response.Token
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.function.BiPredicate

class SignUpViewModel : ViewModel() {

    private val accountService by lazy { RetrofitInstance.accountService }

    val postSignUpRes = MutableLiveData<Res<Token>?>()

    fun postSignUp(signUpRequest: SignUpRequest) {
        accountService.postSignUp(signUpRequest).enqueue(
            object : Callback<Token> {
                override fun onResponse(call: Call<Token>, response: Response<Token>) {
                    Log.d("postSignUp", "${response.code()}: ${response.body()}")
                    postSignUpRes.postValue(Res(response.code(), response.body()!!))
                }

                override fun onFailure(call: Call<Token>, t: Throwable) {
                    Log.d("postSignUp", t.message.toString())
                    postSignUpRes.postValue(null)
                }

            }
        )
    }
}