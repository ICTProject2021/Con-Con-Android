package com.example.a2021ictproject.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a2021ictproject.network.`object`.RetrofitInstance
import com.example.a2021ictproject.network.dao.ContestService
import com.example.a2021ictproject.network.dto.response.Contest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val contestList = MutableLiveData(listOf<Contest>())

    private val contestService: ContestService by lazy { RetrofitInstance.contestService }

    fun getContestLiveDataObserver() : MutableLiveData<List<Contest>> {
        Log.d("vm", contestList.value.toString())
        return contestList
    }

    fun callApi() {
        val call : Call<List<Contest>> = contestService.getContest();
        call.enqueue(object : retrofit2.Callback<List<Contest>>{
            override fun onResponse(call: Call<List<Contest>>, response: Response<List<Contest>>) {
                Log.d("mainViewModel", "${response.code()}-${response.message()}: ${response.body()}")

                if (response.isSuccessful){
                    contestList.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<Contest>>, t: Throwable) {
                contestList.postValue(null)
            }
        })
    }
}