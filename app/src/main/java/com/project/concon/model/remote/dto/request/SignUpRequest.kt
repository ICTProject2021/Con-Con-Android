/**
 * 회원가입 시 사용하는 request class
 * */
package com.project.concon.model.remote.dto.request

data class SignUpRequest(
    val id: String,
    val pw: String,
    val phonenum: String,
    val nickname: String
)
