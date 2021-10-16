package com.example.a2021ictproject.viewmodel

import android.telecom.Call
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a2021ictproject.network.`object`.RetrofitInstance
import com.example.a2021ictproject.network.dao.AccountService
import com.example.a2021ictproject.network.dto.response.Contest
import com.example.a2021ictproject.network.dto.response.Profile
import retrofit2.Response
import retrofit2.http.GET

class ProfileViewModel : ViewModel() {

    private val profileList: MutableLiveData<Profile> = MutableLiveData()
    private val accountService : AccountService by lazy { RetrofitInstance.accountService }

    fun getProfileLiveDataObserver() : MutableLiveData<Profile> {
        return profileList
    }

    fun callApi() {
        accountService.getProfile().enqueue(object : retrofit2.Callback<Profile> {
            override fun onResponse(call: retrofit2.Call<Profile>, response: Response<Profile>) {
                profileList.postValue(response.body())
            }

            override fun onFailure(call: retrofit2.Call<Profile>, t: Throwable) {
                t.printStackTrace()
                profileList.postValue(null)
            }

        })
    }
}