package com.example.a2021ictproject.network.dao

import com.example.a2021ictproject.network.dto.request.ContestRequest
import com.example.a2021ictproject.network.dto.response.Contest
import com.example.a2021ictproject.network.dto.response.ContestDetail
import com.example.a2021ictproject.network.dto.response.Participant
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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
    fun getParticipantInfo(@Path("id") id: String): Call<List<Participant>>

    /* 대회 참여 */
    @POST("/participant/{id}")
    fun postParticipant(
        @Path("id") id: String,
        @Body content: String
    )
}