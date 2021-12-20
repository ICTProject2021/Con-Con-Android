package com.project.concon.model.remote.dao

import com.project.concon.model.remote.dto.Res
import com.project.concon.model.remote.dto.request.ContestRequest
import com.project.concon.model.remote.dto.request.WinnerRequest
import com.project.concon.model.remote.dto.response.Contest
import com.project.concon.model.remote.dto.response.ContestDetail
import com.project.concon.model.remote.dto.response.Participant
import com.project.concon.model.remote.dto.response.Winner
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ContestService {
    /* 대회 조회 */
    @GET("/contest")
    fun getContestList(): Single<Response<Res<List<Contest>>>>

    /* 대회 세부 내용 조회 */
    @GET("/contest/{id}")
    fun getContestDetail(@Path("id") id: Int): Single<Response<Res<ContestDetail>>>

    /* 대회 생성 */
    @POST("/contest")
    fun postCreateContest(@Body contestRequest: ContestRequest): Single<Response<Res<Any>>>

    /* 대회 참여자 조회 */
    @GET("/participant/{id}")
    fun getParticipantList(@Path("id") id: Int): Single<Response<Res<List<Participant>>>>

    /* 대회 참여 */
    @Multipart
    @POST("/participant/{id}")
    fun postParticipate(
        @Path("id") id: Int,
        @Part("content") content: RequestBody,
        @Part attachment: List<MultipartBody.Part>
    ): Single<Response<Res<Any>>>

    /* 좋아요 */
    @PUT("/participant/{cont}/{part}")
    fun putLike(
        @Path("cont") contId: Int,
        @Path("part") partId: Int
    ): Single<Response<Res<Any>>>

    /* 우승자 리스트 가져오기 */
    @GET("/prize/{contestid}")
    fun getWinnerList(@Path("contestid") contestid: Int) : Single<Response<Res<List<Winner>>>>

    /* 대회 우승자 정하기 */
    @PUT("/prize/{contestid}")
    fun putContestWinnerSelect(
        @Path("contestid") contestid: Int,
        @Body winnerRequest: List<WinnerRequest>
    ) : Single<Response<Res<Any>>>

    /* 내가 참가한 대회 조회 */
    @GET("/profile/contest")
    fun getMyParticipatedContest() : Single<Response<Res<List<Contest>>>>
}