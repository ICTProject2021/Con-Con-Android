package com.project.concon.viewmodel

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.concon.network.`object`.RetrofitInstance
import com.project.concon.network.dto.response.Msg
import com.project.concon.network.dto.response.Participant
import com.project.concon.utils.getImageBody
import com.project.concon.utils.getRequestBody
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinContestViewModel : ViewModel() {
    val content = MutableLiveData("")
    val fileList = MutableLiveData<List<Uri>>(listOf())

    private val service by lazy { RetrofitInstance.contestService }

    val getParticipantInfoRes = MutableLiveData<List<Participant>?>()
    val postParticipantRes = MutableLiveData<String?>()
    val isSuccessPutLikes = MutableLiveData<String?>()

    val isLoading = MutableLiveData(false)

    fun getParticipantInfo(id: Int) {
        isLoading.value = true

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

                    isLoading.value = false
                }

                override fun onFailure(call: Call<List<Participant>>, t: Throwable) {
                    Log.d(tag, t.message.toString())
                    getParticipantInfoRes.postValue(null)
                    isLoading.value = false
                }
            }
        )
    }

    fun postParticipant(id: Int, contentResolver: ContentResolver) {
        isLoading.value = true

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

                    isLoading.value = false
                }

                override fun onFailure(call: Call<Msg>, t: Throwable) {
                    Log.d(tag, t.message.toString())
                    postParticipantRes.postValue(null)
                    isLoading.value = false
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