package com.project.concon.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

class BaseViewModel : ViewModel() {
    protected val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    protected val isLoading = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}