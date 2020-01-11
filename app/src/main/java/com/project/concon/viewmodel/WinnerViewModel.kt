package com.project.concon.viewmodel

import androidx.lifecycle.MutableLiveData
import com.project.concon.view.base.BaseViewModel
import com.project.concon.model.remote.dto.response.Winner
import com.project.concon.model.repository.ContestRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class WinnerViewModel @Inject constructor(
    private val repository: ContestRepository
) : BaseViewModel() {

    val isSuccess = MutableLiveData<List<Winner>>()
    val isFailure = MutableLiveData<String>()

    fun getWinnerList(contestId: Int) {
        repository.getWinnerList(contestId)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isSuccess.postValue(it)
            }, {
                isFailure.postValue(it.message)
            }).apply { disposable.add(this) }
    }
}