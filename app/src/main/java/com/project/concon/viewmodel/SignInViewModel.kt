package com.project.concon.viewmodel

import androidx.lifecycle.MutableLiveData
import com.project.concon.base.BaseViewModel
import com.project.concon.model.remote.dto.request.SignInRequest
import com.project.concon.model.repository.AccountRepository
import com.project.concon.widget.livedata.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val repository: AccountRepository
) : BaseViewModel() {
    val id = MutableLiveData<String?>()
    val pw = MutableLiveData<String?>()
    val idErr = MutableLiveData<String>()
    val pwErr = MutableLiveData<String>()

    val onClose = SingleLiveEvent<Unit>()

    val isSuccess = MutableLiveData<String>()
    val isFailure = MutableLiveData<String>()

    fun closeEvent() {
        onClose.call()
    }

    fun postSignIn() {
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