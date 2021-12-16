package com.project.concon.model.remote.dao

import com.project.concon.model.remote.dto.request.IdRequest
import com.project.concon.model.remote.dto.request.SignInRequest
import com.project.concon.model.remote.dto.request.SignUpRequest
import com.project.concon.model.remote.dto.response.Msg
import com.project.concon.model.remote.dto.response.ParticipatedContest
import com.project.concon.model.remote.dto.response.Profile
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AccountService {
    /* 로그인 */
    @POST("/signin")
    fun postSignIn(@Body signInRequest: SignInRequest): Call<Msg>

    /* 회원가입 */
    @POST("/signup")
    fun postSignUp(@Body signUpRequest: SignUpRequest): Call<Msg>

    /* 아이디 중복체크 */
    @POST("/check")
    fun postCheckId(@Body id: IdRequest): Call<Msg>

    /* 프로필 조회 */
    @GET("/profile")
    fun getProfile(): Call<Profile>

    /* 내가 참가한 대회 조회 */
    @GET("/profile/contest")
    fun getMyContest() : Call<List<ParticipatedContest>>
}