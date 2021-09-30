package com.example.a2021ictproject.network.dao

import com.example.a2021ictproject.network.dto.request.SignInRequest
import com.example.a2021ictproject.network.dto.request.SignUpRequest
import com.example.a2021ictproject.network.dto.response.Profile
import com.example.a2021ictproject.network.dto.response.Token
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AccountService {
    /* 틀린 부분이 있으면 수정해주세요 */

    /* 로그인 */
    @POST("/signin")
    fun postSignIn(@Body signInRequest: SignInRequest): Call<Token>

    /* 회원가입 */
    @POST("/signup")
    fun postSignUp(@Body signUpRequest: SignUpRequest): Call<Token>

    /* 프로필 조회 */
    @GET("/profile")
    fun getProfile(): Call<Profile>
}