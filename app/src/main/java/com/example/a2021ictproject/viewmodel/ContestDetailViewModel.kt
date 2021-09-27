package com.example.a2021ictproject.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a2021ictproject.network.`object`.RetrofitInstance
import com.example.a2021ictproject.network.dto.response.ContestDetail
import com.example.a2021ictproject.network.dto.response.Res
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class ContestDetailViewModel : ViewModel() {
    private val contestService by lazy { RetrofitInstance.contestService }

    val getContestDetailRes = MutableLiveData<Res<ContestDetail>?>()

    fun longToDateAsString(date: Long): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(date)
    }

    fun getContestDetail(id: Int) {
        contestService.getContestDetail(id).enqueue(
            object : Callback<ContestDetail> {
                override fun onResponse(call: Call<ContestDetail>, response: Response<ContestDetail>) {
                    Log.d("getContestDetail", "${response.code()}: ${response.body()}")
                    getContestDetailRes.postValue(Res(response.code(), response.body()!!))
                }

                override fun onFailure(call: Call<ContestDetail>, t: Throwable) {
                    Log.d("getContestDetail", t.message.toString())
                    getContestDetailRes.postValue(null)
                }
            }
        )
    }

}