package com.example.a2021ictproject.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a2021ictproject.network.`object`.RetrofitInstance
import com.example.a2021ictproject.network.dto.request.ContestRequest
import com.example.a2021ictproject.network.dto.response.Res
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat

class CreateContestViewModel : ViewModel() {

    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val date = MutableLiveData<String>()
    val prize = MutableLiveData<String>()

    private val contestService by lazy { RetrofitInstance.contestService }

    val postCreateContestRes = MutableLiveData<Res<String>?>()

    fun longTimeToDateAsString(time: Long): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(time)
    }

    fun postCreateContest(contestRequest: ContestRequest) {
        contestService.postCreateContest(contestRequest).enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.d("postCreateContest", "${response.code()}: ${response.body()}")
                    postCreateContestRes.postValue(Res(response.code(), response.body().toString()))
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("postCreateContest", t.message.toString())
                    postCreateContestRes.postValue(null)
                }
            }
        )
    }
}