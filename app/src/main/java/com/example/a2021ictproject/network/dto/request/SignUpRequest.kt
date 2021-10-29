/**
 * 회원가입 시 사용하는 request class
 * */
package com.example.a2021ictproject.network.dto.request

data class SignUpRequest(
    val id: String,
    val pw: String,
    val phonenum: String,
    val nickname: String
)
