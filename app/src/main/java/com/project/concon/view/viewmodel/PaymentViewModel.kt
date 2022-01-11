package com.project.concon.view.viewmodel

import androidx.lifecycle.MutableLiveData
import com.project.concon.view.base.BaseViewModel
import com.project.concon.model.remote.dto.request.CashRequest
import com.project.concon.model.repository.AccountRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class PaymentViewModel @Inject constructor(
    private val repository: AccountRepository
) : BaseViewModel() {

    val isSuccess = MutableLiveData<String>()
    val isFailure = MutableLiveData<String>()

    fun putCharge(cash: Int) {
        startLoading()
        repository.putChargeCash(CashRequest(cash))
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isSuccess.postValue(it)
                stopLoading()
            }, {
                isFailure.postValue(it.message)
                stopLoading()
            }).apply { disposable.add(this) }
    }
}