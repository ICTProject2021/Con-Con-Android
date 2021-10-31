package com.project.concon.network.`object`

import com.google.gson.Gson
import com.project.concon.network.dao.AccountService
import com.project.concon.network.dao.ContestService
import com.project.concon.utils.TokenInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://ec2-18-219-111-210.us-east-2.compute.amazonaws.com:3000/"

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
