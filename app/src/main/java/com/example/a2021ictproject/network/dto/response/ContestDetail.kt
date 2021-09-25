package com.example.a2021ictproject.network.dto.response

import java.sql.Timestamp

data class ContestDetail(
    val title: String,
    val context: String,
    val duedate: Timestamp,
    val host: String
)
