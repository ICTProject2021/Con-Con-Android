package com.project.concon.viewmodel

import androidx.lifecycle.MutableLiveData
import com.project.concon.base.BaseViewModel
import com.project.concon.model.remote.dto.request.WinnerRequest
import com.project.concon.model.remote.dto.response.Participant
import com.project.concon.model.repository.ContestRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class WinnerSelectViewModel (
    private val repository: ContestRepository
) : BaseViewModel() {
    val isSuccessGetParticipantInfo = MutableLiveData<List<Participant>>()
    val isSuccessPutContestWinnerSelect = MutableLiveData<String>()
    val isFailure = MutableLiveData<String>()

    fun getParticipantInfo(id: Int) {
        addDisposable(repository.getParticipantList(id), {
            isSuccessGetParticipantInfo.postValue(it as List<Participant>)
        }, {
            isFailure.postValue(it.message)
        })
    }

    fun putContestWinnerSelect(id: Int, winnerList: List<WinnerRequest>) {
        addDisposable(repository.putContestWinnerSelect(id, winnerList), {
            isSuccessPutContestWinnerSelect.postValue(it as String)
        }, {
            isFailure.postValue(it.message)
        })
    }
}