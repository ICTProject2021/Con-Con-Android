package com.example.a2021ictproject.viewmodel

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a2021ictproject.network.`object`.RetrofitInstance
import com.example.a2021ictproject.network.dao.ContestService
import com.example.a2021ictproject.network.dto.response.Msg
import com.example.a2021ictproject.network.dto.response.Participant
import com.example.a2021ictproject.network.dto.response.Res
import com.example.a2021ictproject.utils.getImageBody
import com.example.a2021ictproject.utils.getRequestBody
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
    val isSuccessPutLikes = MutableLiveData<String?>()

    fun getParticipantInfo(id: Int) {
        val tag = "getParticipantInfo"

        service.getParticipantInfo(id).enqueue(
            object : Callback<List<Participant>> {
                override fun onResponse(
                    call: Call<List<Participant>>,
                    response: Response<List<Participant>>
                ) {
                    Log.d(tag, response.raw().toString())
                    Log.d(tag, response.body().toString())
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

    fun postParticipant(id: Int, contentResolver: ContentResolver) {
        val tag = "postParticipant"

        val content = this.content.value!!.getRequestBody()

        val list = mutableListOf<MultipartBody.Part>()
        Log.d("list-size", fileList.value!!.size.toString())
        fileList.value!!.forEach {
            list.add(it.getImageBody("attachment", contentResolver))
        }

        service.postParticipant(id, content, list).enqueue(
            object : Callback<Msg> {
                override fun onResponse(call: Call<Msg>, response: Response<Msg>) {
                    Log.d(tag, "${response.raw()}\n${response.body()}")
                    if (response.isSuccessful) {
                        if (response.body()!!.msg == "success")
                            postParticipantRes.postValue(response.body()!!.msg)
                    }
                }

                override fun onFailure(call: Call<Msg>, t: Throwable) {
                    Log.d(tag, t.message.toString())
                    postParticipantRes.postValue(null)
                }

            }
        )
    }

    fun putLikes(contId: Int, partId: Int) {
        val tag = "putLikes"

        service.putLikes(contId, partId).enqueue(
            object : Callback<Msg> {
                override fun onResponse(call: Call<Msg>, response: Response<Msg>) {
                    Log.d(tag, "${response.raw()}\n${response.body()}")
                    if (response.isSuccessful) {
                        val it = response.body()?.msg
                        if (it == "success")
                            isSuccessPutLikes.postValue(it)
                    }
                }

                override fun onFailure(call: Call<Msg>, t: Throwable) {
                    Log.e(tag, t.message.toString())
                }
            }
        )
    }
}