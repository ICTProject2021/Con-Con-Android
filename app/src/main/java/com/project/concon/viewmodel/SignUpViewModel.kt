package com.project.concon.viewmodel

import androidx.lifecycle.MutableLiveData
import com.project.concon.base.BaseViewModel
import com.project.concon.model.remote.dto.request.IdRequest
import com.project.concon.model.remote.dto.request.SignUpRequest
import com.project.concon.model.repository.AccountRepository
import com.project.concon.widget.livedata.SingleLiveEvent
import com.project.concon.widget.utils.isNotBlankAll
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val repository: AccountRepository
) : BaseViewModel() {

    val onClose = SingleLiveEvent<Unit>()

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

    val isSuccessCheckId = MutableLiveData<String>()
    val isSuccessSignUp = MutableLiveData<String>()
    val isFailure = MutableLiveData<String>()

    fun closeEvent() {
        onClose.call()
    }

    fun postCheckId() {
        addDisposable(repository.postCheckId(IdRequest(id.value?:"")), {
            isSuccessCheckId.postValue(it as String)
        }, {
            isFailure.postValue(it.message)
        })
    }

    fun signUp() {
        val signUpReq = SignUpRequest(
            id.value?:"",
            password.value?:"",
            phoneNumber.value?:"",
            nickname.value?:""
        )

        startLoading()
        addDisposable(repository.postSignUp(signUpReq), {
            isSuccessSignUp.postValue(it as String)
            stopLoading()
        }, {
            isFailure.postValue(it.message)
            stopLoading()
        })
    }

    // 리스트의 요소의 값 중 하나가 공백이면 false
    fun signUpBtnEnabled() {
        btnEnabled.value =
            listOf(id.value, password.value, phoneNumber.value, nickname.value).isNotBlankAll()
    }
}

