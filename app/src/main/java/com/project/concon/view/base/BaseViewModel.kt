package com.project.concon.view.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    protected val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    val isLoading = MutableLiveData<Boolean>()

    protected fun startLoading() {
        isLoading.postValue(true)
    }

    protected fun stopLoading() {
        isLoading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}