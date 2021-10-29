package com.example.a2021ictproject.utils

import com.example.a2021ictproject.utils.PreferenceUtils.token
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader("authorization", token?: "").build()
        return chain.proceed(request)
    }
}