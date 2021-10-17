package com.example.a2021ictproject.network.dto.response

data class ContestDetail(
    val id: Int,
    val title: String,
    val content: String,
    val startdate: Long,
    val duedate: Long,
    val host: String,
    val prize: Prize
)
