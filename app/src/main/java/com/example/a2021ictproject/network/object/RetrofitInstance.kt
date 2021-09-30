package com.example.a2021ictproject.network.`object`

import com.example.a2021ictproject.network.dao.AccountService
import com.example.a2021ictproject.network.dao.ContestService
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

object RetrofitInstance {
    private const val BASE_URL = "http://1234/"

    private val gson = Gson().newBuilder().create()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val accountService: AccountService = retrofit.create(AccountService::class.java)
    val contestService: ContestService = retrofit.create(ContestService::class.java)
}