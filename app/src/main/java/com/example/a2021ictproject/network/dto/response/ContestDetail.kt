package com.example.a2021ictproject.network.dto.response

import java.sql.Timestamp

data class ContestDetail(
    val title: String,
    val content: String,
    val duedate: Long,
    val host: String
)
