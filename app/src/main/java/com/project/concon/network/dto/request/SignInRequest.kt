/**
 * 로그인 시 사용하는 request class
 * */
package com.project.concon.network.dto.request

data class SignInRequest(
    val id: String,
    val pw: String
)
