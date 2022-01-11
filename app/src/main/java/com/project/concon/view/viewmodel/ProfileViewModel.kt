package com.project.concon.view.viewmodel

import androidx.lifecycle.MutableLiveData
import com.project.concon.view.base.BaseViewModel
import com.project.concon.model.remote.dto.response.Profile
import com.project.concon.model.repository.AccountRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val repository: AccountRepository
) : BaseViewModel() {

    val isSuccess = MutableLiveData<Profile>()
    val isFailure = MutableLiveData<String>()

    fun getProfile() {
        repository.getProfile()
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isSuccess.postValue(it)
            }, {
                isFailure.postValue(it.message)
            }).apply { disposable.add(this) }
    }
}