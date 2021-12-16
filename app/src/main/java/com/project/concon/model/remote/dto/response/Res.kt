package com.project.concon.model.remote.dto.response

data class Res<T>(
    val code: Int?,
    val result: T?
)
