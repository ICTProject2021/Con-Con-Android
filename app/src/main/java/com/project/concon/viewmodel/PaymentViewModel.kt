package com.project.concon.viewmodel

import androidx.lifecycle.MutableLiveData
import com.project.concon.base.BaseViewModel
import com.project.concon.model.remote.dto.request.CashRequest
import com.project.concon.model.repository.AccountRepository
import com.project.concon.widget.livedata.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class PaymentViewModel (
    private val repository: AccountRepository
) : BaseViewModel() {
    val onCloseEvent = SingleLiveEvent<Unit>()

    val isSuccess = MutableLiveData<String>()
    val isFailure = MutableLiveData<String>()

    fun closeEvent() {
        onCloseEvent.call()
    }

    fun chargeCash(cash: Int) {
        startLoading()
        addDisposable(repository.putChargeCash(CashRequest(cash)), {
            isSuccess.postValue(it as String)
            stopLoading()
        }, {
            isFailure.postValue(it.message)
            stopLoading()
        })
    }
}