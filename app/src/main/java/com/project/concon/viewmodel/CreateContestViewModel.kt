package com.project.concon.viewmodel

import androidx.lifecycle.MutableLiveData
import com.project.concon.base.BaseViewModel
import com.project.concon.model.remote.dto.response.Prize
import com.project.concon.model.repository.ContestRepository
import com.project.concon.widget.extension.SECOND
import com.project.concon.widget.extension.getDateAsString
import com.project.concon.widget.utils.getMultipartBody
import com.project.concon.widget.utils.getRequestBody
import okhttp3.MultipartBody

class CreateContestViewModel (
    private val repository: ContestRepository
) : BaseViewModel() {
    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val date = MutableLiveData<String>()
    val startTime = MutableLiveData<Long>()
    val dueTime = MutableLiveData<Long>()
    val prize = MutableLiveData<String>()
    val attachment = MutableLiveData<List<MultipartBody.Part>>()

    val isSuccess = MutableLiveData<String>()
    val isFailure = MutableLiveData<String>()

    fun createContest(prizeList: List<Prize>) {
        startLoading()
        val params = mapOf(
            "title" to title.value!!.getRequestBody(),
            "content" to content.value!!.getRequestBody(),
            "startdate" to startTime.value!!.getDateAsString(SECOND).getRequestBody(),
            "duedate" to dueTime.value!!.getDateAsString(SECOND).getRequestBody()
        )

        addDisposable(repository.postCreateContest(attachment.value?: listOf(), params, prizeList.map { it.getMultipartBody() }), {
            isSuccess.postValue(it as String)
            stopLoading()
        }, {
            isFailure.postValue(it.message)
            stopLoading()
        })
    }
}