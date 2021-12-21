package com.project.concon.viewmodel

import androidx.lifecycle.MutableLiveData
import com.project.concon.base.BaseViewModel
import com.project.concon.model.remote.dto.request.WinnerRequest
import com.project.concon.model.remote.dto.response.Participant
import com.project.concon.model.repository.ContestRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class WinnerSelectViewModel @Inject constructor(
    private val repository: ContestRepository
) : BaseViewModel() {

    val isSuccessGetParticipantInfo = MutableLiveData<List<Participant>>()
    val isSuccessPutContestWinnerSelect = MutableLiveData<String>()
    val isFailure = MutableLiveData<String>()

    fun getParticipantInfo(id: Int) {
        repository.getParticipantList(id)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isSuccessGetParticipantInfo.value = it
            }, {
                isFailure.value = it.message
            }).apply { disposable.add(this) }
    }

    fun putContestWinnerSelect(id: Int, winnerList: List<WinnerRequest>) {
        repository.putContestWinnerSelect(id, winnerList)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isSuccessPutContestWinnerSelect.value = it
            }, {
                isFailure.value = it.message
            }).apply { disposable.add(this) }
    }
}