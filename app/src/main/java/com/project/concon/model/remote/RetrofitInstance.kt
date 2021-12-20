package com.project.concon.model.remote

import com.google.gson.Gson
import com.project.concon.model.remote.dao.AccountService
import com.project.concon.model.remote.dao.ContestService
import com.project.concon.utils.PreferenceUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    const val BASE_URL = "http://ec2-18-216-110-95.us-east-2.compute.amazonaws.com:3000/"

    private val gson = Gson().newBuilder().setLenient().create()

    private val okHttpClient: OkHttpClient =
        OkHttpClient().newBuilder().addInterceptor(TokenInterceptor()).build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    val accountService: AccountService = retrofit.create(AccountService::class.java)
    val contestService: ContestService = retrofit.create(ContestService::class.java)
}

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader("authorization", PreferenceUtils.token
            ?: "").build()
        return chain.proceed(request)
    }
}