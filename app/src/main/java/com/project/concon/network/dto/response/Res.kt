package com.project.concon.network.dto.response

data class Res<T>(
    val code: Int?,
    val result: T?
)
