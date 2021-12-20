package com.project.concon.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.concon.model.remote.RetrofitInstance
import com.project.concon.model.remote.dto.request.ContestRequest
import com.project.concon.model.remote.dto.response.Msg
import com.project.concon.model.repository.ContestRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import javax.inject.Inject

class CreateContestViewModel @Inject constructor(private val contestRepository: ContestRepository) :
    ViewModel() {

    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val date = MutableLiveData<String>()
    val prize = MutableLiveData<String>()
    var startTime = MutableLiveData<Long>()
    var dueTime = MutableLiveData<Long>()

    val isSuccess = MutableLiveData<String>()
    val isFailure: MutableLiveData<String> = MutableLiveData()

    val isLoading = MutableLiveData(false)

    fun getDateAsString(time: Long): String =
        SimpleDateFormat("yyyy-MM-dd").format(time)

    fun getDateAsString(time: Long?): String =
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss").format(time)

//    fun postCreateContest(contestRequest: ContestRequest) {
//        isLoading.value = true
//
//        contestService.postCreateContest(contestRequest).enqueue(
//            object : Callback<Msg> {
//                override fun onResponse(call: Call<Msg>, response: Response<Msg>) {
//                    Log.d("createContest", "${response.code()}: ${response.body()}")
//                    Log.d("createContest", response.raw().toString())
//                    postCreateContestRes.postValue(response.body())
//                    isLoading.value = false
//                }
//
//                override fun onFailure(call: Call<Msg>, t: Throwable) {
//                    Log.d("postCreateContest", t.message.toString())
//                    postCreateContestRes.postValue(null)
//                    isLoading.value = false
//                }
//            }
//        )
//    }

    fun createContest(contestRequest: ContestRequest) {
        isLoading.value = true

        contestRepository.postCreateContest(contestRequest).observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread()).subscribe({
                isSuccess.postValue(it)
                isLoading.value = false
            }, {
                isFailure.postValue(it.message)
                isLoading.value = false
            })
    }
}