package com.example.a2021ictproject.network.dto.response

data class Contest(
    val id: Int,
    val dueDate: Long,
    val profile: String,
    val host: String
)