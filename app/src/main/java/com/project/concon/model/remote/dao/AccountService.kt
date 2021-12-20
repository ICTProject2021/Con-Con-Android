package com.project.concon.model.remote.dao

import com.project.concon.model.remote.dto.Res
import com.project.concon.model.remote.dto.request.CashRequest
import com.project.concon.model.remote.dto.request.IdRequest
import com.project.concon.model.remote.dto.request.SignInRequest
import com.project.concon.model.remote.dto.request.SignUpRequest
import com.project.concon.model.remote.dto.response.Profile
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface AccountService {
    /* 로그인 */
    @POST("/signin")
    fun postSignIn(@Body signInRequest: SignInRequest): Single<Response<Res<Any>>>

    /* 회원가입 */
    @POST("/signup")
    fun postSignUp(@Body signUpRequest: SignUpRequest): Single<Response<Res<Any>>>

    /* 아이디 중복체크 */
    @POST("/check")
    fun postCheckId(@Body id: IdRequest): Single<Response<Res<Any>>>

    /* 프로필 조회 */
    @GET("/profile")
    fun getProfile(): Single<Response<Res<Profile>>>

    /* 캐시 충전 */
    @PUT("/charge")
    fun putChargeCash(@Body cash: CashRequest): Single<Response<Res<Any>>>
}