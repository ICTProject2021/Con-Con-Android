package com.project.concon.utils
import com.project.concon.utils.PreferenceUtils.token
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader("authorization", token?: "").build()
        return chain.proceed(request)
    }
}