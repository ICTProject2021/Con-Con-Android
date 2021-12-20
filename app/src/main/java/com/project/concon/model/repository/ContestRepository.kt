package com.project.concon.model.repository

import com.project.concon.model.remote.BaseRemote
import com.project.concon.model.remote.dao.ContestService
import com.project.concon.model.remote.dto.request.ContestRequest
import com.project.concon.model.remote.dto.request.WinnerRequest
import com.project.concon.model.remote.dto.response.Contest
import com.project.concon.model.remote.dto.response.ContestDetail
import com.project.concon.model.remote.dto.response.Participant
import com.project.concon.model.remote.dto.response.Winner
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ContestRepository(
    override val service: ContestService
) : BaseRemote<ContestService>() {

    fun getContestList(): Single<List<Contest>> =
        service.getContestList().map(getResponse())

    fun getContestDetail(id: Int): Single<ContestDetail> =
        service.getContestDetail(id).map(getResponse())

    fun postCreateContest(contestRequest: ContestRequest): Single<String> =
        service.postCreateContest(contestRequest).map(getMessage())

    fun getParticipantList(id: Int): Single<List<Participant>> =
        service.getParticipantList(id).map(getResponse())

    fun postParticipate(
        id: Int,
        content: RequestBody,
        attachment: List<MultipartBody.Part>
    ): Single<String> =
        service.postParticipate(id, content, attachment).map(getMessage())

    fun putLike(contId: Int, partId: Int): Single<String> =
        service.putLike(contId, partId).map(getMessage())

    fun getWinnerList(contId: Int): Single<List<Winner>> =
        service.getWinnerList(contId).map(getResponse())

    fun putContestWinnerSelect(contId: Int, winnerRequest: List<WinnerRequest>): Single<String> =
        service.putContestWinnerSelect(contId, winnerRequest).map(getMessage())

    fun getMyParticipatedContest(): Single<List<Contest>> =
        service.getMyParticipatedContest().map(getResponse())
}