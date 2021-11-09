package com.project.concon.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.concon.network.`object`.RetrofitInstance
import com.project.concon.network.dto.request.CashRequest
import com.project.concon.network.dto.response.Msg
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentViewModel : ViewModel() {
    private val contestService by lazy {
        RetrofitInstance.contestService
    }

    val isSuccess = MutableLiveData<String>()
    val isFailure = MutableLiveData<String>()

    fun putCharge(cash: Int) {
        contestService.putCharge(CashRequest(cash)).enqueue(
            object : Callback<Msg> {
                override fun onResponse(call: Call<Msg>, response: Response<Msg>) {
                    Log.d("putCharge", "${response.raw()}")

                    when (response.body()?.msg) {
                        "success" -> isSuccess.value = "success"
                        else -> isFailure.value = "fail"
                    }
                }

                override fun onFailure(call: Call<Msg>, t: Throwable) {
                    Log.d("putCharge", t.message.toString())
                    isFailure.value = t.message
                }
            }
        )
    }
}