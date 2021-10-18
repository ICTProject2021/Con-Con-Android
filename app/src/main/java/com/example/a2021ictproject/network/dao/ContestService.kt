package com.example.a2021ictproject.network.dao

import com.example.a2021ictproject.network.dto.request.ContestRequest
import com.example.a2021ictproject.network.dto.response.Contest
import com.example.a2021ictproject.network.dto.response.ContestDetail
import com.example.a2021ictproject.network.dto.response.Participant
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ContestService {
    /* 틀린 부분이 있으면 수정해주세요 */

    /* 대회 조회 */
    @GET("/contest")
    fun getContest(): Call<List<Contest>>

    /* 대회 세부 내용 조회 */
    @GET("/contest/{id}")
    fun getContestDetail(@Path("id") id: Int): Call<ContestDetail>

    /* 대회 생성 */
    @POST("/contest/create")
    fun postCreateContest(@Body contestRequest: ContestRequest): Call<String>

    /* 대회 참여 조회 */
    @GET("/participant/{id}")
    fun getParticipantInfo(@Path("id") id: Int): Call<List<Participant>>

    /* 대회 참여 */
    @Multipart
    @POST("/participant/{id}")
    fun postParticipant(
        @Path("id") id: Int,
        @Part content: RequestBody,
        @Part attachment: MultipartBody.Part
    ): Call<String>
}