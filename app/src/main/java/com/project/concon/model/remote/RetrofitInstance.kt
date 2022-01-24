package com.project.concon.model.remote

import com.google.gson.Gson
import com.project.concon.model.remote.dao.AccountService
import com.project.concon.model.remote.dao.ContestService
import com.project.concon.widget.utils.PreferenceUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    const val BASE_URL = "http://ec2-18-191-238-179.us-east-2.compute.amazonaws.com:3000/"

    private val gson = Gson().newBuilder().setLenient().create()

    private val okHttpClient: OkHttpClient =
        OkHttpClient().newBuilder().addInterceptor(TokenInterceptor()).build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val accountService: AccountService = retrofit.create(AccountService::class.java)
    val contestService: ContestService = retrofit.create(ContestService::class.java)
}

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader(
            "Authorization", PreferenceUtils.token ?: "").build()
        return chain.proceed(request)
    }
}