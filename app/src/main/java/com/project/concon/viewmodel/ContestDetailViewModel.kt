package com.project.concon.viewmodel

import androidx.lifecycle.MutableLiveData
import com.project.concon.base.BaseViewModel
import com.project.concon.model.remote.dto.response.ContestDetail
import com.project.concon.model.remote.dto.response.Prize
import com.project.concon.model.repository.ContestRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

class ContestDetailViewModel @Inject constructor(
    private val repository: ContestRepository
) : BaseViewModel() {

    val isSuccess = MutableLiveData<ContestDetail>()
    val isFailure = MutableLiveData<String>()

    fun toDate(date: String): String {
        return if (date.isNotEmpty()) {
            date.substring(0, 10)
        } else {
            ""
        }
    }

    fun getAllPriceSum(list: List<Prize>): String {
        var price = 0
        list.forEach {
            price += it.price
        }
        val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
        val cash = numberFormat.format(price)
        return "총합 ${cash}원"
    }

    fun getContestDetail(id: Int) {
        isLoading.value = true

        repository.getContestDetail(id)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isSuccess.value = it
                isLoading.value = false
            }, {
                isFailure.value = it.message
                isLoading.value = false
            }).apply { disposable.add(this) }
    }
}

