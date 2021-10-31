package com.project.concon.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.concon.network.`object`.RetrofitInstance
import com.project.concon.network.dao.AccountService
import com.project.concon.network.dto.response.ParticipatedContest
import retrofit2.Call
import retrofit2.Response

class ParticipatedContestViewModel: ViewModel() {
    private val participatedLiveData: MutableLiveData<List<ParticipatedContest>> = MutableLiveData()
    private val accountService : AccountService by lazy { RetrofitInstance.accountService }

    fun getObserver() : MutableLiveData<List<ParticipatedContest>> {
        return participatedLiveData
    }

    fun callApi() {
        accountService.getMyContest().enqueue(object : retrofit2.Callback<List<ParticipatedContest>> {
            override fun onResponse(
                call: Call<List<ParticipatedContest>>,
                response: Response<List<ParticipatedContest>>,
            ) {
                Log.d("getMyContest", "${response.code()}-${response.message()}: ${response.body()}")
                if (response.isSuccessful) {
                    participatedLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<ParticipatedContest>>, t: Throwable) {
                Log.d("getMyContest", t.message.toString())
                participatedLiveData.postValue(null)
            }
        })
    }
}