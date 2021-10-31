package com.project.concon.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.concon.network.`object`.RetrofitInstance
import com.project.concon.network.dto.request.ContestRequest
import com.project.concon.network.dto.response.Msg
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class CreateContestViewModel : ViewModel() {

    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val date = MutableLiveData<String>()
    val prize = MutableLiveData<String>()
    var startTime = MutableLiveData<Long>()
    var dueTime = MutableLiveData<Long>()

    private val contestService by lazy { RetrofitInstance.contestService }

    val postCreateContestRes = MutableLiveData<Msg?>()

    fun longTimeToDateAsString(time: Long): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(time)
    }

    fun postCreateContest(contestRequest: ContestRequest) {
        contestService.postCreateContest(contestRequest).enqueue(
            object : Callback<Msg> {
                override fun onResponse(call: Call<Msg>, response: Response<Msg>) {
                    Log.d("createContest", "${response.code()}: ${response.body()}")
                    Log.d("createContest", response.raw().toString())
                    postCreateContestRes.postValue(response.body())
                }

                override fun onFailure(call: Call<Msg>, t: Throwable) {
                    Log.d("postCreateContest", t.message.toString())
                    postCreateContestRes.postValue(null)
                }
            }
        )
    }
}