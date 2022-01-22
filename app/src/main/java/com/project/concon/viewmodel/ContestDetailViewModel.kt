package com.project.concon.viewmodel

import androidx.lifecycle.MutableLiveData
import com.project.concon.base.BaseViewModel
import com.project.concon.model.remote.dto.response.ContestDetail
import com.project.concon.model.remote.dto.response.Prize
import com.project.concon.model.repository.ContestRepository
import com.project.concon.widget.livedata.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

class ContestDetailViewModel @Inject constructor(
    private val repository: ContestRepository
) : BaseViewModel() {
    val onBack = SingleLiveEvent<Unit>()
    val onJoin = SingleLiveEvent<Unit>()

    val isSuccess = MutableLiveData<ContestDetail>()
    val isFailure = MutableLiveData<String>()

    fun backEvent() {
        onBack.call()
    }

    fun joinEvent() {
        onJoin.call()
    }

    fun toDate(date: String): String {
        return if (date.isNotEmpty()) {
            date.substring(0, 10)
        } else {
            ""
        }
    }

    fun getAllPriceSum(): String {
        var price = 0
        isSuccess.value!!.prize.forEach {
            price += it.price
        }
        val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
        val cash = numberFormat.format(price)
        return "총합 ${cash}원"
    }

    fun getContestDetail(id: Int) {
        startLoading()
        addDisposable(repository.getContestDetail(id), {
            isSuccess.postValue(it as ContestDetail)
            stopLoading()
        }, {
            isFailure.postValue(it.message)
            stopLoading()
        })
    }
}

