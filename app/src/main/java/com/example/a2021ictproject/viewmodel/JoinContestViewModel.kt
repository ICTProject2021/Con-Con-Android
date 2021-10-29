package com.example.a2021ictproject.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a2021ictproject.network.`object`.RetrofitInstance
import com.example.a2021ictproject.network.dao.ContestService
import com.example.a2021ictproject.network.dto.response.Participant
import com.example.a2021ictproject.network.dto.response.Res
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class JoinContestViewModel : ViewModel() {

    val content = MutableLiveData("")
    val fileList = MutableLiveData<List<Uri>>(listOf())

    private val service by lazy { RetrofitInstance.contestService }

    val getParticipantInfoRes = MutableLiveData<List<Participant>?>()
    val postParticipantRes = MutableLiveData<String?>()

    fun getParticipantInfo(id: Int) {
        val tag = "getParticipantInfo"

        service.getParticipantInfo(id).enqueue(
            object : Callback<List<Participant>> {
                override fun onResponse(
                    call: Call<List<Participant>>,
                    response: Response<List<Participant>>
                ) {
                    Log.d(tag, response.raw().toString())
                    if (response.isSuccessful)
                        getParticipantInfoRes.postValue(response.body())
                }

                override fun onFailure(call: Call<List<Participant>>, t: Throwable) {
                    Log.d(tag, t.message.toString())
                    getParticipantInfoRes.postValue(null)
                }
            }
        )
    }

    fun postParticipant() {
        val tag = "postParticipant"

        val id = 1
        val content = this.content.value!!.toRequestBody("text/plain".toMediaTypeOrNull())
        var file: MultipartBody.Part?

        if (fileList.value!!.isNotEmpty()) {
            val builder = MultipartBody.Builder()
        } else {
            return
        }

//        service.postParticipant(id, content, file).enqueue(
//            object : Callback<String> {
//                override fun onResponse(call: Call<String>, response: Response<String>) {
//                    Log.d(tag, response.raw().toString())
//                    if (response.isSuccessful)
//                        postParticipantRes.postValue(response.body())
//                }
//
//                override fun onFailure(call: Call<String>, t: Throwable) {
//                    Log.d(tag, t.message.toString())
//                    postParticipantRes.postValue(null)
//                }
//
//            }
//        )
    }
}