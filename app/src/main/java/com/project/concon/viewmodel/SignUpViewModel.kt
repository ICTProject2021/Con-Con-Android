package com.project.concon.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.concon.network.`object`.RetrofitInstance
import com.project.concon.network.dto.request.IdRequest
import com.project.concon.network.dto.request.SignUpRequest
import com.project.concon.network.dto.response.Msg
import com.project.concon.utils.isNotBlankAll
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {

    val id = MutableLiveData<String>()
    val idCheck = MutableLiveData<Boolean>()
    val password = MutableLiveData<String>()
    val phoneNumber = MutableLiveData<String>()
    val nickname = MutableLiveData<String>()

    val idErr = MutableLiveData("")
    val pwErr = MutableLiveData("")
    val phoneErr = MutableLiveData("")
    val nicknameErr = MutableLiveData("")

    val btnEnabled = MutableLiveData(false)

    private val accountService by lazy { RetrofitInstance.accountService }

    val postCheckIdRes = MutableLiveData<Msg?>()
    val postSignUpRes = MutableLiveData<Msg?>()

    val isLoading = MutableLiveData(false)

    fun postCheckId() {
        if (id.value.isNullOrBlank()) return

        val idReq = IdRequest(id.value!!)

        accountService.postCheckId(idReq).enqueue(
            object : Callback<Msg> {
                override fun onResponse(call: Call<Msg>, response: Response<Msg>) {
                    Log.d("postCheckId", "${response.code()}-${response.message()}: ${response.body()}")

                    if (response.isSuccessful)
                        postCheckIdRes.postValue(response.body())
                }

                override fun onFailure(call: Call<Msg>, t: Throwable) {
                    Log.d("postCheckId", t.message.toString())
                    postSignUpRes.postValue(null)
                }
            }
        )
    }

    fun postSignUp() {
        if (!idCheck.value!!) {
            // todo 다이얼로그 띄우기
            Log.d("signUpViewModel", "중복 체크 안 함")
            return
        }

        isLoading.value = true

        val signUpReq = SignUpRequest(
            id.value!!,
            password.value!!,
            phoneNumber.value!!,
            nickname.value!!
        )

        accountService.postSignUp(signUpReq).enqueue(
            object : Callback<Msg> {
                override fun onResponse(call: Call<Msg>, response: Response<Msg>) {
                    Log.d("postSignUp", "${response.code()}: ${response.body()}")
                    Log.d("postSignUp", response.raw().toString())

                    if (response.isSuccessful)
                        postSignUpRes.postValue(response.body())

                    isLoading.value = false
                }

                override fun onFailure(call: Call<Msg>, t: Throwable) {
                    Log.d("postSignUp", t.message.toString())
                    isLoading.value = false
                }
            }
        )
    }

    // 리스트의 요소의 값 중 하나가 공백이면 false
    fun signUpBtnEnabled() {
        btnEnabled.value =
            listOf(id.value, password.value, phoneNumber.value, nickname.value).isNotBlankAll()
    }
}

