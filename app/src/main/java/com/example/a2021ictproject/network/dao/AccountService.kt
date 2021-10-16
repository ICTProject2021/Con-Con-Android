package com.example.a2021ictproject.network.dao

import com.example.a2021ictproject.network.dto.request.IdRequest
import com.example.a2021ictproject.network.dto.request.SignInRequest
import com.example.a2021ictproject.network.dto.request.SignUpRequest
import com.example.a2021ictproject.network.dto.response.Profile
import com.example.a2021ictproject.network.dto.response.Token
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AccountService {
    /* 로그인 */
    @POST("/signin")
    fun postSignIn(@Body signInRequest: SignInRequest): Call<String>

    /* 회원가입 */
    @POST("/signup")
    fun postSignUp(@Body signUpRequest: SignUpRequest): Call<String>

    /* 아이디 중복체크 */
    @POST("check")
    fun postCheckId(@Body id: IdRequest): Call<String>

    /* 프로필 조회 */
    @GET("/profile")
    fun getProfile(): Call<Profile>
}