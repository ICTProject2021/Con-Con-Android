package com.project.concon.viewmodel

import androidx.lifecycle.MutableLiveData
import com.project.concon.base.BaseViewModel
import com.project.concon.model.remote.dto.response.Profile
import com.project.concon.model.repository.AccountRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ProfileViewModel (
    private val repository: AccountRepository
) : BaseViewModel() {

    val isSuccess = MutableLiveData<Profile>()
    val isFailure = MutableLiveData<String>()

    fun getProfile() {
        addDisposable(repository.getProfile(), {
            isSuccess.postValue(it as Profile)
        }, {
            isFailure.postValue(it.message)
        })
    }
}