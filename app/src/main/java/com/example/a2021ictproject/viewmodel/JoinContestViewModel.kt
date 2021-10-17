package com.example.a2021ictproject.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a2021ictproject.network.`object`.RetrofitInstance
import com.example.a2021ictproject.network.dao.ContestService
import com.example.a2021ictproject.network.dto.response.Participant
import com.example.a2021ictproject.network.dto.response.Res
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class JoinContestViewModel : ViewModel() {

    private val service by lazy { RetrofitInstance.contestService }

    val getParticipantInfoRes = MutableLiveData<Res<List<Participant>>?>()

    fun getParticipantInfo(id: Int) {
        service.getParticipantInfo(id).enqueue(
            object : Callback<List<Participant>> {
                override fun onResponse(
                    call: Call<List<Participant>>,
                    response: Response<List<Participant>>
                ) {
                    Log.d("getParticipantInfo", "${response.code()}: ${response.body()}")
                    getParticipantInfoRes.postValue(Res(response.code(), response.body()))
                }

                override fun onFailure(call: Call<List<Participant>>, t: Throwable) {
                    Log.d("getParticipantInfo", t.message.toString())
                    getParticipantInfoRes.postValue(null)
                }

            }
        )
    }
}