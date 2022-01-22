package com.project.concon.viewmodel

import androidx.lifecycle.MutableLiveData
import com.project.concon.base.BaseViewModel
import com.project.concon.model.remote.dto.response.Winner
import com.project.concon.model.repository.ContestRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class WinnerViewModel @Inject constructor(
    private val repository: ContestRepository
) : BaseViewModel() {

    val isSuccess = MutableLiveData<List<Winner>>()
    val isFailure = MutableLiveData<String>()

    fun getWinnerList(id: Int) {
        addDisposable(repository.getWinnerList(id), {
            isSuccess.postValue(it as List<Winner>)
        }, {
            isFailure.postValue(it.message)
        })
    }
}