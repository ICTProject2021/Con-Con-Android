package com.project.concon.network.dao

import com.project.concon.network.dto.request.CashRequest
import com.project.concon.network.dto.request.ContestRequest
import com.project.concon.network.dto.request.WinnerPrizeRequest
import com.project.concon.network.dto.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ContestService {
    /* 대회 조회 */
    @GET("/contest")
    fun getContest(): Call<List<Contest>>

    /* 대회 세부 내용 조회 */
    @GET("/contest/{id}")
    fun getContestDetail(@Path("id") id: Int): Call<ContestDetail>

    /* 대회 생성 */
    @POST("/contest")
    fun postCreateContest(@Body contestRequest: ContestRequest): Call<Msg>

    /* 대회 참여 조회 */
    @GET("/participant/{id}")
    fun getParticipantInfo(@Path("id") id: Int): Call<List<Participant>>

    /* 대회 참여 */
    @Multipart
    @POST("/participant/{id}")
    fun postParticipant(
        @Path("id") id: Int,
        @Part("content") content: RequestBody,
        @Part attachment: List<MultipartBody.Part>
    ): Call<Msg>

    /* 좋아유~~ */
    @PUT("/participant/{cont}/{part}")
    fun putLikes(
        @Path("cont") contId: Int,
        @Path("part") partId: Int
    ): Call<Msg>

    /* 캐시 충전 */
    @PUT("/charge")
    fun putCharge(@Body cash: CashRequest): Call<Msg>

    @GET("/prize/{contestid}")
    fun winnerList(@Path("contestid") contestid: Int) : Call<List<Winner>>

    @PUT("/prize/{contestid}")
    fun winnerPrizeSelect(@Path("contestid") contestid: Int, @Body winnerPrizeRequest: List<WinnerPrizeRequest>) : Call<Msg>
}