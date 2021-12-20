package com.project.concon.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.concon.model.remote.RetrofitInstance
import com.project.concon.model.remote.dao.ContestService
import com.project.concon.model.remote.dto.response.Contest
import com.project.concon.model.repository.ContestRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.schedulers.Schedulers.io
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val contestRepository: ContestRepository) : ViewModel() {

    private val isSuccess = MutableLiveData(listOf<Contest>())
    private val isFailure: MutableLiveData<String> = MutableLiveData()

    val isLoading = MutableLiveData(false)

    fun getContestLiveDataObserver(): MutableLiveData<List<Contest>> {
        return isSuccess
    }

    fun getContestList() {
        isLoading.value = true

        contestRepository.getContestList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            isSuccess.postValue(it)
            isLoading.value = false
        }, {
            isFailure.postValue(it.message)
            isLoading.value = false
        })
    }
//
//
//    fun callApi() {
//        isLoading.value = true
//
//        val call : Call<List<Contest>> = contestService.getContest();
//        call.enqueue(object : retrofit2.Callback<List<Contest>>{
//            override fun onResponse(call: Call<List<Contest>>, response: Response<List<Contest>>) {
//                Log.d("getContest", "${response.code()}-${response.message()}: ${response.body()}")
//
//                if (response.isSuccessful){
//                    contestList.postValue(response.body())
//                }
//
//                isLoading.value = false
//            }
//
//            override fun onFailure(call: Call<List<Contest>>, t: Throwable) {
//                Log.d("getContest", t.message.toString())
//                contestList.postValue(null)
//                isLoading.value = false
//            }
//        })
//    }


}