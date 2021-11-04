package com.project.concon.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.concon.network.`object`.RetrofitInstance
import com.project.concon.network.dao.ContestService
import com.project.concon.network.dto.response.Winner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WinnerViewModel : ViewModel() {
    val winnerLiveData = MutableLiveData<List<Winner>?>()
    private val contestService: ContestService by lazy { RetrofitInstance.contestService }

    fun callWinnerList(contestId: String) {
        contestService.winnerList(contestId).enqueue(object : Callback<List<Winner>> {
            override fun onResponse(call: Call<List<Winner>>, response: Response<List<Winner>>) {
                if (response.isSuccessful) {
                    winnerLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<Winner>>, t: Throwable) {
                winnerLiveData.postValue(null)
            }
        })
    }
}