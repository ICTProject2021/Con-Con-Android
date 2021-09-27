/**
 * 대회 생성 시 사용하는 request class
 * */
package com.example.a2021ictproject.network.dto.request

data class ContestRequest(
    val title: String,
    val content: String,
    val dueDate: Long,
    val prize: Int
)
