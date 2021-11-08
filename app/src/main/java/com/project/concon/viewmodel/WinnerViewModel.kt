package com.project.concon.viewmodel

import android.util.Log
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

    fun callWinnerList(contestId: Int) {
        contestService.getWinnerList(contestId).enqueue(object : Callback<List<Winner>> {
            override fun onResponse(call: Call<List<Winner>>, response: Response<List<Winner>>) {
                if (response.isSuccessful) {
                    Log.d("getWinnerList", "${response.code()}-${response.message()}: ${response.body()}")
                    Log.d("getWinnerList", response.raw().toString())
                    winnerLiveData.postValue(response.body())
                } else {
                    winnerLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<List<Winner>>, t: Throwable) {
                winnerLiveData.postValue(null)
            }
        })
    }
}