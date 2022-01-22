package com.project.concon.viewmodel

import androidx.lifecycle.MutableLiveData
import com.project.concon.base.BaseViewModel
import com.project.concon.model.remote.dto.response.Prize
import com.project.concon.widget.livedata.SingleLiveEvent

class PrizeViewModel : BaseViewModel() {
    val onAddEvent = SingleLiveEvent<Unit>()
    val onBackEvent = SingleLiveEvent<Unit>()

    val prizeList = MutableLiveData<List<Prize>>(listOf())

    fun addEvent() {
        onAddEvent.call()
    }

    fun backEvent() {
        onBackEvent.call()
    }
}