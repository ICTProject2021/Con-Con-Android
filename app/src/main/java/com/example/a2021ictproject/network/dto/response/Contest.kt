package com.example.a2021ictproject.network.dto.response

import java.sql.Date

data class Contest(
    val ID: Int?,
    val title: String?,
    val content: String?,
    val startdate: String?,
    val duedate: String?,
    val host: String?,
    val profile: String?
)