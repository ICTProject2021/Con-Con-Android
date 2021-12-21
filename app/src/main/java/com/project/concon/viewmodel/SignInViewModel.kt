package com.project.concon.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.concon.model.remote.RetrofitInstance
import com.project.concon.model.remote.dao.AccountService
import com.project.concon.model.remote.dto.request.SignInRequest
import com.project.concon.model.remote.dto.response.Msg
import com.project.concon.model.repository.AccountRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {

    val id = MutableLiveData<String?>()
    val pw = MutableLiveData<String?>()
    val idErr = MutableLiveData<String>()
    val pwErr = MutableLiveData<String>()

    private val _isLoading = MutableLiveData(false)
    val isLoading = _isLoading

    fun postSignIn() {
        _isLoading.value = true

        val req = SignInRequest(id.value!!, pw.value!!)


    }

    override fun onCleared() {
        super.onCleared()

    }
}