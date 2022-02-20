package com.project.concon.model.remote.dto

data class Res<T>(
    val data: T?,
    val response: Int,
    val message: String?
)
