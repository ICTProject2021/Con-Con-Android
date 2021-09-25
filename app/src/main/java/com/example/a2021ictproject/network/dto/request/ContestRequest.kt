package com.example.a2021ictproject.network.dto.request

import java.sql.Timestamp
import java.util.*

data class ContestRequest(
    val title: String,
    val content: String,
    val dueDate: Timestamp,
    val prize: Int
)
