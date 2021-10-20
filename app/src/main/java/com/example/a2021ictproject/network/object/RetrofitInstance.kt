package com.example.a2021ictproject.network.`object`

import com.example.a2021ictproject.network.dao.AccountService
import com.example.a2021ictproject.network.dao.ContestService
import com.example.a2021ictproject.utils.TokenInterceptor
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

object RetrofitInstance {
    private const val BASE_URL = "http://18.219.111.210:3000/"

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
