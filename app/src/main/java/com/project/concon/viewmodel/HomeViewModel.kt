package com.project.concon.viewmodel

import androidx.lifecycle.MutableLiveData
import com.project.concon.base.BaseViewModel
import com.project.concon.model.remote.dto.response.Contest
import com.project.concon.model.repository.ContestRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel (
    private val repository: ContestRepository
) : BaseViewModel() {
    val isSuccess = MutableLiveData<List<Contest>>()
    val isFailure = MutableLiveData<String>()

    fun getContestList() {
        startLoading()
        addDisposable(repository.getContestList(), {
            isSuccess.postValue(it as List<Contest>)
            stopLoading()
        }, {
            isFailure.postValue(it.message)
            stopLoading()
        })
    }
}