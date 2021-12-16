/**
 * 로그인 시 사용하는 request class
 * */
package com.project.concon.model.remote.dto.request

data class SignInRequest(
    val id: String,
    val pw: String
)
