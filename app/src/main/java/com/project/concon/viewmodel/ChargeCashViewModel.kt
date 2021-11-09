package com.project.concon.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.concon.network.`object`.RetrofitInstance
import com.project.concon.network.dao.ContestService
import com.project.concon.network.dto.request.CashRequest
import com.project.concon.network.dto.response.Msg
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*

/**
 * 캐시 충전 프래그먼트 (인앱결제 구현 전) - 삭제 예정
 * */
class ChargeCashViewModel : ViewModel() {
    // ChargeCash
    val cash = MutableLiveData("")
    val error = MutableLiveData("")
    val cashStack = MutableLiveData<Stack<Int>>()
    private val stack = Stack<Int>()

    // ChargeCashFinish
    val msg = MutableLiveData("")

    private val contestService: ContestService by lazy { RetrofitInstance.contestService }
    val putChargeRes = MutableLiveData<Msg?>()

    fun moneyFormat(num: Int): String {
        cash.value += num.toString()

        return NumberFormat.getInstance(Locale.getDefault()).format(cash.value) + "원"
    }

    fun pushNumber(num: Int) {
        stack.push(num)
        cashStack.value = stack
    }

    fun popNumber() {
        stack.pop()
        cashStack.value = stack
    }

    fun putCharge() {
        val tag = "putCharge"

        contestService.putCharge(CashRequest(cash.value!!.toInt())).enqueue(
            object : Callback<Msg> {
                override fun onResponse(call: Call<Msg>, response: Response<Msg>) {
                    Log.d(tag, response.raw().toString())

                    if (response.isSuccessful)
                        putChargeRes.postValue(response.body())
                }

                override fun onFailure(call: Call<Msg>, t: Throwable) {
                    Log.d(tag, t.message.toString())
                    putChargeRes.postValue(null)
                }

            }
        )
    }

    fun anime() {
        // 아무튼 애니메이션 코드인데 시간 걍 다이얼로그 띄울예정
    }
}

