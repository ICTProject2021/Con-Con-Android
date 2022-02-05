package com.project.concon.viewmodel

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.project.concon.base.BaseViewModel
import com.project.concon.model.remote.dto.response.Participant
import com.project.concon.model.repository.ContestRepository
import com.project.concon.widget.livedata.SingleLiveEvent
import com.project.concon.widget.utils.getImageBody
import com.project.concon.widget.utils.getRequestBody
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MultipartBody
import javax.inject.Inject

class JoinContestViewModel (
    private val contestRepository: ContestRepository
) : BaseViewModel() {
    val content = MutableLiveData("")
    val fileList = MutableLiveData<List<Uri>>(listOf())

    val onBackEvent = SingleLiveEvent<Unit>()
    val onSendEvent = SingleLiveEvent<Unit>()
    val onSelectPhotoEvent = SingleLiveEvent<Unit>()

    val isSuccessGetParticipantInfo = MutableLiveData<List<Participant>>()
    val isSuccessPostParticipate = MutableLiveData<String>()
    val isSuccessPutLikes = MutableLiveData<String>()
    val isFailure = MutableLiveData<String>()

    fun backEvent() {
        onBackEvent.call()
    }

    fun sendEvent() {
        onSendEvent.call()
    }

    fun selectPhotoEvent() {
        onSelectPhotoEvent.call()
    }

    fun deleteAllPhotoEvent() {
        fileList.value = listOf()
    }

    fun getParticipantInfo(id: Int) {
        startLoading()
        addDisposable(contestRepository.getParticipantList(id), {
            isSuccessGetParticipantInfo.postValue(it as List<Participant>)
            stopLoading()
        }, {
            isFailure.postValue(it.message)
            stopLoading()
        })
    }

    fun participate(id: Int, context: Context) {
        val content = content.value!!.getRequestBody()
        val list = mutableListOf<MultipartBody.Part>()

        fileList.value!!.forEach {
            list.add(it.getImageBody("attachment", context))
        }

        addDisposable(contestRepository.postParticipate(id, content, list), {
            isSuccessPostParticipate.postValue(it as String)
        }, {
            isFailure.postValue(it.message)
        })
    }

    fun like(contId: Int, partId: Int) {
        addDisposable(contestRepository.putLike(contId, partId), {
            isSuccessPutLikes.postValue(it as String)
        }, {
            isFailure.postValue(it.message)
        })
    }
}