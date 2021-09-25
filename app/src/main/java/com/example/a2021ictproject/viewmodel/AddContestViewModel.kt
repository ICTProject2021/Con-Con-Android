package com.example.a2021ictproject.viewmodel

import androidx.lifecycle.ViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat

class AddContestViewModel : ViewModel() {

    fun longTimeToDateAsString(time: Long): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(time)
    }
}