package com.project.concon.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.concon.model.remote.dto.response.Prize

class PrizeDialogViewModel : ViewModel() {

    var prize = MutableLiveData<Int>()
    var prizeList = MutableLiveData<List<Prize>>(listOf())

    fun setPrize(prize: Int) {
        this.prize.value = prize
    }

    fun getPrize() : LiveData<Int> {
        return prize
    }

    fun setPrizeList(prizeList: List<Prize>) {
        this.prizeList.value = prizeList
    }

    fun addPrizeList(prize: Prize) {
        var list = mutableListOf<Prize>()
        prizeList.value?.forEach { list.add(it) }
        list.add(prize)

        prizeList.value = list
    }

    fun getLastRank(): Int {
        return if (prizeList.value!!.isNotEmpty()) {
            val lastIdx = prizeList.value!!.size - 1
            prizeList.value!![lastIdx].rank
        } else {
            0
        }
    }
}