/**
 * 로그인 시 사용하는 request class
 * */
package com.example.a2021ictproject.network.dto.request

data class SignInRequest(
    val id: String,
    val pw: String
)
