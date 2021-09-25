package com.example.a2021ictproject.network.dto.request

data class SignUpRequest(
    val id: String,
    val pw: String,
    val phone: String,
    val nickname: String
)
