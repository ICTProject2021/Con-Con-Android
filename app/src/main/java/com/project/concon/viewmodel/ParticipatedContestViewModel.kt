package com.project.concon.viewmodel

import androidx.lifecycle.MutableLiveData
import com.project.concon.view.base.BaseViewModel
import com.project.concon.model.remote.dto.response.Contest
import com.project.concon.model.repository.ContestRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ParticipatedContestViewModel @Inject constructor(
    private val contestRepository: ContestRepository
): BaseViewModel() {
    val isSuccess: MutableLiveData<List<Contest>> = MutableLiveData()
    val isFailure: MutableLiveData<String> = MutableLiveData()

    fun getMyContest() {
        isLoading.value = true
        contestRepository.getMyParticipatedContest()
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isSuccess.postValue(it)
                isLoading.postValue(false)
            }, {
                isFailure.postValue(it.message)
                isLoading.value = false
            }).apply { disposable.add(this) }
    }
}