package com.project.concon.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.concon.model.remote.dto.request.ContestRequest
import com.project.concon.model.repository.ContestRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import javax.inject.Inject

class CreateContestViewModel @Inject constructor(
    private val contestRepository: ContestRepository
) : ViewModel() {

    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val date = MutableLiveData<String>()
    val prize = MutableLiveData<String>()
    var startTime = MutableLiveData<Long>()
    var dueTime = MutableLiveData<Long>()

    val isSuccess = MutableLiveData<String>()
    val isFailure: MutableLiveData<String> = MutableLiveData()

    private val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    val isLoading = MutableLiveData(false)

    fun getDateAsString(time: Long): String =
        SimpleDateFormat("yyyy-MM-dd").format(time)

    fun getDateAsString(time: Long?): String =
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss").format(time)

    fun createContest(contestRequest: ContestRequest) {
        isLoading.value = true

        contestRepository.postCreateContest(contestRequest).observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread()).subscribe({
                isSuccess.postValue(it)
                isLoading.value = false
            }, {
                isFailure.postValue(it.message)
                isLoading.value = false
            }).apply { disposable.add(this) }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}