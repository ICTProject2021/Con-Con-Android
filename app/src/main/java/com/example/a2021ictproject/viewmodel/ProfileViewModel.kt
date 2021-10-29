package com.example.a2021ictproject.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a2021ictproject.network.`object`.RetrofitInstance
import com.example.a2021ictproject.network.dao.AccountService
import com.example.a2021ictproject.network.dto.response.Profile
import retrofit2.Response

class ProfileViewModel : ViewModel() {

    private val profileList: MutableLiveData<Profile> = MutableLiveData()
    private val accountService : AccountService by lazy { RetrofitInstance.accountService }

    fun getProfileLiveDataObserver() : MutableLiveData<Profile> {
        return profileList
    }

    fun callApi() {
        val tag = "getProfile"

        accountService.getProfile().enqueue(object : retrofit2.Callback<Profile> {
            override fun onResponse(call: retrofit2.Call<Profile>, response: Response<Profile>) {
                Log.d(tag, response.raw().toString())
                Log.d(tag, response.body().toString())
                if (response.isSuccessful)
                    profileList.postValue(response.body())
            }

            override fun onFailure(call: retrofit2.Call<Profile>, t: Throwable) {
                Log.d(tag, t.message.toString())
                profileList.postValue(null)
            }

        })
    }
}