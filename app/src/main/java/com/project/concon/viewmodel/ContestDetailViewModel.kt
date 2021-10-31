package com.project.concon.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.concon.network.`object`.RetrofitInstance
import com.project.concon.network.dto.response.ContestDetail
import com.project.concon.network.dto.response.Prize
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContestDetailViewModel : ViewModel() {

    private val contestService by lazy { RetrofitInstance.contestService }

    val getContestDetailRes = MutableLiveData<ContestDetail?>()

    fun toDate(date: String): String {
        return if (date.isNotEmpty()) {
            date.substring(0, 10)
        } else {
            ""
        }
    }

    fun getAllPriceSum(list: List<Prize>): String {
        var price = 0
        list.forEach {
            price += it.price
        }
        return "총합 ${price}원"
    }

    fun getContestDetail(id: Int) {
        contestService.getContestDetail(id).enqueue(
            object : Callback<ContestDetail> {
                override fun onResponse(call: Call<ContestDetail>, response: Response<ContestDetail>) {
                    Log.d("getContestDetail", "${response.code()}-${response.message()}: ${response.body()}")
                    Log.d("getContestDetail", response.raw().toString())

                    if (response.isSuccessful)
                        getContestDetailRes.postValue(response.body())
                }

                override fun onFailure(call: Call<ContestDetail>, t: Throwable) {
                    Log.d("getContestDetail", t.message.toString())

                    getContestDetailRes.postValue(null)
                }
            }
        )
    }

}

