package com.project.concon.viewmodel

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.concon.model.remote.RetrofitInstance
import com.project.concon.model.remote.dto.response.Msg
import com.project.concon.model.remote.dto.response.Participant
import com.project.concon.model.repository.ContestRepository
import com.project.concon.utils.getImageBody
import com.project.concon.utils.getRequestBody
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class JoinContestViewModel @Inject constructor(private val contestRepository: ContestRepository) : ViewModel() {
    val content = MutableLiveData("")
    val fileList = MutableLiveData<List<Uri>>(listOf())

    val getParticipantInfoRes = MutableLiveData<List<Participant>?>()
    val postParticipantRes = MutableLiveData<String?>()
    val isSuccessPutLikes = MutableLiveData<String?>()

    val isFailure: MutableLiveData<String> = MutableLiveData()

    val isLoading = MutableLiveData(false)

//    fun getParticipantInfo(id: Int) {
//        isLoading.value = true
//
//        val tag = "getParticipantInfo"
//
//        service.getParticipantInfo(id).enqueue(
//            object : Callback<List<Participant>> {
//                override fun onResponse(
//                    call: Call<List<Participant>>,
//                    response: Response<List<Participant>>
//                ) {
//                    Log.d(tag, response.raw().toString())
//                    Log.d(tag, response.body().toString())
//                    if (response.isSuccessful)
//                        getParticipantInfoRes.postValue(response.body())
//
//                    isLoading.value = false
//                }
//
//                override fun onFailure(call: Call<List<Participant>>, t: Throwable) {
//                    Log.d(tag, t.message.toString())
//                    getParticipantInfoRes.postValue(null)
//                    isLoading.value = false
//                }
//            }
//        )
//    }

    fun getParticipantInfo(id: Int) {
        isLoading.value = true

        contestRepository.getParticipantList(id).observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe({
            getParticipantInfoRes.postValue(it)
            isLoading.value = false
        }, {
            isFailure.postValue(it.message)
            isLoading.value = false
        })

    }

//    fun postParticipant(id: Int, contentResolver: ContentResolver) {
//        isLoading.value = true
//
//        val tag = "postParticipant"
//
//        if (content.value.isNullOrBlank()) {
//            return
//        }
//
//        val content = this.content.value!!.getRequestBody()
//
//        val list = mutableListOf<MultipartBody.Part>()
//        Log.d("list-size", fileList.value!!.size.toString())
//        fileList.value!!.forEach {
//            list.add(it.getImageBody("attachment", contentResolver))
//        }
//
//        service.postParticipant(id, content, list).enqueue(
//            object : Callback<Msg> {
//                override fun onResponse(call: Call<Msg>, response: Response<Msg>) {
//                    Log.d(tag, "${response.raw()}\n${response.body()}")
//                    if (response.isSuccessful) {
//                        if (response.body()!!.msg == "success")
//                            postParticipantRes.postValue(response.body()!!.msg)
//                    }
//
//                    isLoading.value = false
//                }
//
//                override fun onFailure(call: Call<Msg>, t: Throwable) {
//                    Log.d(tag, t.message.toString())
//                    postParticipantRes.postValue(null)
//                    isLoading.value = false
//                }
//            }
//        )
//    }

    fun postParticipant(id: Int, contentResolver: ContentResolver) {
        isLoading.value = true

        val content = this.content.value!!.getRequestBody()

        val list = mutableListOf<MultipartBody.Part>()
        Log.d("list-size", fileList.value!!.size.toString())
        fileList.value!!.forEach {
            list.add(it.getImageBody("attachment", contentResolver))
        }

        contestRepository.postParticipate(id, content, list).observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe({
            postParticipantRes.postValue(it)
        }, {
            isFailure.postValue(it.message)
        })
    }

//    fun putLikes(contId: Int, partId: Int) {
//        val tag = "putLikes"
//
//        service.putLikes(contId, partId).enqueue(
//            object : Callback<Msg> {
//                override fun onResponse(call: Call<Msg>, response: Response<Msg>) {
//                    Log.d(tag, "${response.raw()}\n${response.body()}")
//                    if (response.isSuccessful) {
//                        val it = response.body()?.msg
//                        if (it == "success")
//                            isSuccessPutLikes.postValue(it)
//                    }
//                }
//
//                override fun onFailure(call: Call<Msg>, t: Throwable) {
//                    Log.e(tag, t.message.toString())
//                }
//            }
//        )
//    }

    fun putLikes(contId: Int, partId: Int) {
        contestRepository.putLike(contId, partId).observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe({
            isSuccessPutLikes.postValue(it)
        }, {
            isFailure.postValue(it.message)
        })
    }
}