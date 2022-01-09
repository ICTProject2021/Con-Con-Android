package com.project.concon.viewmodel

import androidx.lifecycle.MutableLiveData
import com.project.concon.base.BaseViewModel
import com.project.concon.model.remote.dto.request.SignInRequest
import com.project.concon.model.repository.AccountRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : BaseViewModel() {

    val id = MutableLiveData<String?>()
    val pw = MutableLiveData<String?>()
    val idErr = MutableLiveData<String>()
    val pwErr = MutableLiveData<String>()

    val isSuccess = MutableLiveData<String>()
    val isFailure = MutableLiveData<String>()

    fun postSignIn() {
        startLoading()
        accountRepository.postSignIn(SignInRequest(id.value!!, pw.value!!))
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isSuccess.postValue(it)
                stopLoading()
            }, {
                isFailure.postValue(it.message)
                stopLoading()
            }).apply { disposable.add(this) }
    }
}