package com.project.concon.viewmodel

import androidx.lifecycle.MutableLiveData
import com.project.concon.base.BaseViewModel
import com.project.concon.model.remote.dto.response.Contest
import com.project.concon.model.repository.ContestRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val contestRepository: ContestRepository
) : BaseViewModel() {

    val isSuccess = MutableLiveData(listOf<Contest>())
    val isFailure: MutableLiveData<String> = MutableLiveData()

    fun getContestList() {
        isLoading.value = true

        contestRepository.getContestList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            isSuccess.postValue(it)
            isLoading.value = false
        }, {
            isFailure.postValue(it.message)
            isLoading.value = false
        }).apply { disposable.add(this) }
    }
}