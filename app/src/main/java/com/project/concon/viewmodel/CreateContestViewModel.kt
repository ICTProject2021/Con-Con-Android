package com.project.concon.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.concon.model.remote.RetrofitInstance
import com.project.concon.model.remote.dto.request.ContestRequest
import com.project.concon.model.remote.dto.response.Msg
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

    val isLoading = MutableLiveData(false)

    fun getDateAsString(time: Long): String =
        SimpleDateFormat("yyyy-MM-dd").format(time)

    fun getDateAsString(time: Long?): String =
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss").format(time)

    fun postCreateContest(contestRequest: ContestRequest) {
        isLoading.value = true

        contestService.postCreateContest(contestRequest).enqueue(
            object : Callback<Msg> {
                override fun onResponse(call: Call<Msg>, response: Response<Msg>) {
                    Log.d("createContest", "${response.code()}: ${response.body()}")
                    Log.d("createContest", response.raw().toString())
                    postCreateContestRes.postValue(response.body())
                    isLoading.value = false
                }

                override fun onFailure(call: Call<Msg>, t: Throwable) {
                    Log.d("postCreateContest", t.message.toString())
                    postCreateContestRes.postValue(null)
                    isLoading.value = false
                }
            }
        )
    }
}