package com.project.concon.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.concon.model.remote.RetrofitInstance
import com.project.concon.model.remote.dao.ContestService
import com.project.concon.model.remote.dto.request.WinnerPrizeRequest
import com.project.concon.model.remote.dto.response.Msg
import com.project.concon.model.remote.dto.response.Participant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WinnerSelectViewModel : ViewModel() {

    val participantList = MutableLiveData<List<Participant>?>()
    val msg = MutableLiveData<Msg?>()
    private val contestService: ContestService by lazy { RetrofitInstance.contestService }

    fun callApi(id: Int) {
        contestService.getParticipantInfo(id).enqueue(object : Callback<List<Participant>> {
            override fun onResponse(
                call: Call<List<Participant>>,
                response: Response<List<Participant>>
            ) {
                participantList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Participant>>, t: Throwable) {
                participantList.postValue(null)
            }
        })
    }

    fun request(id: Int, winnerPrizeRequest: List<WinnerPrizeRequest>) {
        contestService.winnerPrizeSelect(id, winnerPrizeRequest).enqueue(object : Callback<Msg> {
            override fun onResponse(call: Call<Msg>, response: Response<Msg>) {
                if (response.isSuccessful) {
                    Log.d("request", "${response.code()}-${response.message()}: ${response.body()}")
                    Log.d("request", response.raw().toString())
                    msg.postValue(response.body())
                } else {
                    Log.d("request", "${response.code()}-${response.message()}: ${response.body()}")
                    Log.d("request", response.raw().toString())
                    msg.postValue(null)
                }
            }

            override fun onFailure(call: Call<Msg>, t: Throwable) {
                msg.postValue(null)
            }
        })
    }

}