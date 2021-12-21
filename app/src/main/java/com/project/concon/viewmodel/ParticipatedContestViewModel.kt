package com.project.concon.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.concon.base.BaseViewModel
import com.project.concon.model.remote.RetrofitInstance
import com.project.concon.model.remote.dao.AccountService
import com.project.concon.model.remote.dto.response.Contest
import com.project.concon.model.remote.dto.response.ParticipatedContest
import com.project.concon.model.repository.AccountRepository
import com.project.concon.model.repository.ContestRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class ParticipatedContestViewModel @Inject constructor(private val contestRepository: ContestRepository): BaseViewModel() {
    private val participatedLiveData: MutableLiveData<List<Contest>> = MutableLiveData()
    val isFailure: MutableLiveData<String> = MutableLiveData()

    fun getObserver() : MutableLiveData<List<Contest>> {
        return participatedLiveData
    }

//    fun callApi() {
//        accountService.getMyContest().enqueue(object : retrofit2.Callback<List<ParticipatedContest>> {
//            override fun onResponse(
//                call: Call<List<ParticipatedContest>>,
//                response: Response<List<ParticipatedContest>>,
//            ) {
//                Log.d("getMyContest", "${response.code()}-${response.message()}: ${response.body()}")
//                if (response.isSuccessful) {
//                    participatedLiveData.postValue(response.body())
//                }
//            }
//
//            override fun onFailure(call: Call<List<ParticipatedContest>>, t: Throwable) {
//                Log.d("getMyContest", t.message.toString())
//                participatedLiveData.postValue(null)
//            }
//        })
//    }

    fun getMyContest() {
        contestRepository.getMyParticipatedContest().observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe({
            participatedLiveData.postValue(it)
        }, {
            isFailure.postValue(it.message)
        }).apply { disposable.add(this) }
    }
}