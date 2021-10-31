package com.example.a2021ictproject.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a2021ictproject.network.`object`.RetrofitInstance
import com.example.a2021ictproject.network.dao.AccountService
import com.example.a2021ictproject.network.dto.request.SignInRequest
import com.example.a2021ictproject.network.dto.response.Msg
import com.example.a2021ictproject.network.dto.response.Res
import com.example.a2021ictproject.network.dto.response.Token
import com.example.a2021ictproject.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class SignInViewModel : ViewModel() {

    val id = MutableLiveData<String?>()
    val pw = MutableLiveData<String?>()
    val idErr = MutableLiveData<String>()
    val pwErr = MutableLiveData<String>()

    private val accountService: AccountService by lazy { RetrofitInstance.accountService }

    val postSignInRes = MutableLiveData<Msg?>()

    fun postSignIn() {
        val req = SignInRequest(id.value!!, pw.value!!)

        accountService.postSignIn(req).enqueue(
            object : Callback<Msg> {
                override fun onResponse(call: Call<Msg>, response: Response<Msg>) {
                    Log.d("postSignIn", "${response.code()}-${response.message()}: ${response.body()}")
                    Log.d("postSignIn", response.raw().toString())

                    if (response.isSuccessful)
                        postSignInRes.postValue(response.body()!!)
                }

                override fun onFailure(call: Call<Msg>, t: Throwable) {
                    Log.d("postSignIn", t.message.toString())
                    postSignInRes.postValue(null)
                }
            }
        )
    }

}