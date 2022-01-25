package com.project.concon.viewmodel

import androidx.lifecycle.MutableLiveData
import com.project.concon.base.BaseViewModel
import com.project.concon.model.remote.dto.request.ContestRequest
import com.project.concon.model.remote.dto.response.Prize
import com.project.concon.model.repository.ContestRepository
import com.project.concon.widget.extension.SECOND
import com.project.concon.widget.extension.getDateAsString
import com.project.concon.widget.livedata.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import javax.inject.Inject

class CreateContestViewModel (
    private val repository: ContestRepository
) : BaseViewModel() {
    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val date = MutableLiveData<String>()
    var startTime = MutableLiveData<Long>()
    var dueTime = MutableLiveData<Long>()
    val prize = MutableLiveData<String>()

    val isSuccess = MutableLiveData<String>()
    val isFailure: MutableLiveData<String> = MutableLiveData()

    fun createContest(prizeList: List<Prize>) {
        startLoading()

        val contestRequest = ContestRequest(
            title.value!!,
            content.value!!,
            startTime.value!!.getDateAsString(SECOND),
            dueTime.value!!.getDateAsString(SECOND),
            prizeList
        )

        addDisposable(repository.postCreateContest(contestRequest), {
            isSuccess.postValue(it as String)
            stopLoading()
        }, {
            isFailure.postValue(it.message)
            stopLoading()
        })
    }
}