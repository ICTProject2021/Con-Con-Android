package com.project.concon.viewmodel

import androidx.lifecycle.MutableLiveData
import com.project.concon.base.BaseViewModel
import com.project.concon.model.remote.dto.response.Contest
import com.project.concon.model.repository.ContestRepository
import com.project.concon.widget.livedata.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ParticipatedContestViewModel @Inject constructor(
    private val contestRepository: ContestRepository
): BaseViewModel() {
    val onBackEvent = SingleLiveEvent<Unit>()

    val isSuccess = MutableLiveData<List<Contest>>()
    val isFailure = MutableLiveData<String>()

    fun backEvent() {
        onBackEvent.call()
    }

    fun getMyParticipatedContestList() {
        startLoading()
        addDisposable(contestRepository.getMyParticipatedContest(), {
            isSuccess.postValue(it as List<Contest>)
            stopLoading()
        }, {
            isFailure.postValue(it.message)
            stopLoading()
        })
    }
}