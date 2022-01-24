package com.project.concon.viewmodel

import androidx.lifecycle.MutableLiveData
import com.project.concon.base.BaseViewModel
import com.project.concon.model.remote.dto.request.SignInRequest
import com.project.concon.model.repository.AccountRepository

class SignInViewModel (
    private val repository: AccountRepository
) : BaseViewModel() {
    val id = MutableLiveData<String?>()
    val pw = MutableLiveData<String?>()
    val idErr = MutableLiveData<String>()
    val pwErr = MutableLiveData<String>()

    val isSuccess = MutableLiveData<String>()
    val isFailure = MutableLiveData<String>()

    fun signIn() {
        startLoading()
        addDisposable(repository.postSignIn(SignInRequest(id.value?:"", pw.value?:"")), {
            isSuccess.postValue(it as String)
            stopLoading()
        }, {
            isFailure.postValue(it.message)
            stopLoading()
        })
    }
}