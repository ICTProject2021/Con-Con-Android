package com.project.concon.view.viewmodel

import androidx.lifecycle.MutableLiveData
import com.project.concon.view.base.BaseViewModel
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
        startLoading()
        contestRepository.getContestList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isSuccess.postValue(it)
                stopLoading()
            }, {
                isFailure.postValue(it.message)
                stopLoading()
            }).apply { disposable.add(this) }
    }
}