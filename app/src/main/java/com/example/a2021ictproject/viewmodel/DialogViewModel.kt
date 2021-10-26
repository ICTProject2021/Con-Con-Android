package com.example.a2021ictproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a2021ictproject.network.dto.response.Prize

class DialogViewModel : ViewModel() {

    var prize = MutableLiveData<Int>()
    var prizeList = MutableLiveData<List<Prize>>()


    fun setPrize(prize: Int) {
        this.prize.value = prize
    }

    fun getPrize() : LiveData<Int> {
        return prize
    }

    fun setPrizeList(prizeList: List<Prize>) {
        this.prizeList.value = prizeList
    }

}