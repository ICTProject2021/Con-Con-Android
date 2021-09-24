package com.example.a2021ictproject.network.dto.request

data class SignUpRequest(
    val id: String,
    val password: String,
    val phoneNumber: String,
    val nickname: String
)
