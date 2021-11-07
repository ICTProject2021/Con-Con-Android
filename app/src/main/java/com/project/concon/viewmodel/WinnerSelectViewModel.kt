package com.project.concon.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.concon.network.`object`.RetrofitInstance
import com.project.concon.network.dao.ContestService
import com.project.concon.network.dto.request.WinnerPrizeRequest
import com.project.concon.network.dto.response.Msg
import com.project.concon.network.dto.response.Participant
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
                    msg.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<Msg>, t: Throwable) {
                msg.postValue(null)
            }
        })
    }

}