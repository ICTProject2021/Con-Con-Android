package com.example.a2021ictproject.viewmodel

import androidx.lifecycle.ViewModel
import com.example.a2021ictproject.network.`object`.RetrofitInstance
import com.example.a2021ictproject.network.dao.AccountService

class ProfileViewModel : ViewModel() {

    private val accountService : AccountService by lazy { RetrofitInstance.accountService }

    fun getProfile() {

    }

    fun getCash() {

    }

    fun getNickName() {

    }
}