package com.project.concon.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.concon.network.`object`.RetrofitInstance
import com.project.concon.network.dao.ContestService
import com.project.concon.network.dto.response.Contest
import retrofit2.Call
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val contestList = MutableLiveData(listOf<Contest>())

    private val contestService: ContestService by lazy { RetrofitInstance.contestService }

    fun getContestLiveDataObserver() : MutableLiveData<List<Contest>> {
        return contestList
    }

    fun callApi() {
        val call : Call<List<Contest>> = contestService.getContest();
        call.enqueue(object : retrofit2.Callback<List<Contest>>{
            override fun onResponse(call: Call<List<Contest>>, response: Response<List<Contest>>) {
                Log.d("getContest", "${response.code()}-${response.message()}: ${response.body()}")

                if (response.isSuccessful){
                    contestList.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<Contest>>, t: Throwable) {
                Log.d("getContest", t.message.toString())
                contestList.postValue(null)
            }
        })
    }
}