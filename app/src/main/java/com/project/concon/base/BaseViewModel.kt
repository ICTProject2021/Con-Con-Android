package com.project.concon.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class BaseViewModel : ViewModel() {
    private val disposable = CompositeDisposable()

    val isLoading = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    protected fun startLoading() {
        isLoading.postValue(true)
    }

    protected fun stopLoading() {
        isLoading.postValue(false)
    }

    protected fun addDisposable(single: Single<*>, successListener: (Any)->Unit, errorListener: (Throwable)->Unit) {
        disposable.add(single.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Any>() {
                override fun onSuccess(t: Any) {
                    successListener(t)
                }

                override fun onError(e: Throwable) {
                    errorListener(e)
                }
            }) as Disposable)
    }
}