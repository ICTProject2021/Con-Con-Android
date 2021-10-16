package com.example.a2021ictproject.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a2021ictproject.network.`object`.RetrofitInstance
import com.example.a2021ictproject.network.dao.AccountService
import com.example.a2021ictproject.network.dto.request.SignInRequest
import com.example.a2021ictproject.network.dto.response.Res
import com.example.a2021ictproject.network.dto.response.Token
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInViewModel : ViewModel() {

    private val accountService: AccountService by lazy { RetrofitInstance.accountService }

    val postSignInRes = MutableLiveData<Res<String>?>()

    fun postSignIn(signInRequest: SignInRequest) {
        accountService.postSignIn(signInRequest).enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.d("postSignIn", "${response.code()}: ${response.body()}")
                    postSignInRes.postValue(Res(response.code(), response.body()!!))
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("postSignIn", t.message.toString())
                    postSignInRes.postValue(null)
                }
            }
        )
    }

}