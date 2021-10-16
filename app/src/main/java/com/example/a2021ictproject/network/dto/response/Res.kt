package com.example.a2021ictproject.network.dto.response

data class Res<T>(
    val code: Int,
    val result: T?
)
