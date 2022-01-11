package com.project.concon.view.viewmodel

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.project.concon.view.base.BaseViewModel
import com.project.concon.model.remote.dto.response.Participant
import com.project.concon.model.repository.ContestRepository
import com.project.concon.utils.getImageBody
import com.project.concon.utils.getRequestBody
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MultipartBody
import javax.inject.Inject

class JoinContestViewModel @Inject constructor(
    private val contestRepository: ContestRepository
) : BaseViewModel() {

    val content = MutableLiveData("")
    val fileList = MutableLiveData<List<Uri>>(listOf())

    val isSuccessGetParticipantInfo = MutableLiveData<List<Participant>>()
    val isSuccessPostParticipate = MutableLiveData<String>()
    val isSuccessPutLikes = MutableLiveData<String>()
    val isFailure = MutableLiveData<String>()

    fun getParticipantInfo(id: Int) {
        startLoading()
        contestRepository.getParticipantList(id)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isSuccessGetParticipantInfo.postValue(it)
                stopLoading()
            }, {
                isFailure.postValue(it.message)
                stopLoading()
            }).apply { disposable.add(this) }
    }

    fun postParticipant(id: Int, contentResolver: ContentResolver) {
        val content = this.content.value!!.getRequestBody()
        val list = mutableListOf<MultipartBody.Part>()

        fileList.value!!.forEach {
            list.add(it.getImageBody("attachment", contentResolver))
        }

        contestRepository.postParticipate(id, content, list)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isSuccessPostParticipate.postValue(it)
            }, {
                isFailure.postValue(it.message)
            }).apply { disposable.add(this) }
    }

    fun putLikes(contId: Int, partId: Int) {
        contestRepository.putLike(contId, partId)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isSuccessPutLikes.postValue(it)
            }, {
                isFailure.postValue(it.message)
            }).apply { disposable.add(this) }
    }
}