package com.example.a2021ictproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DialogViewModel : ViewModel() {

    var prize = MutableLiveData<Int>()

    fun setPrize(prize: Int) {
        this.prize.value = prize
    }

    fun getPrize() : LiveData<Int> {
        return prize
    }

}