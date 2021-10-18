package com.example.a2021ictproject.network.dto.response

data class Participant(
    val nickname: String,
    val profile: String,
    val content: String,
    val likes: Int,
    val URL: List<String>?
)