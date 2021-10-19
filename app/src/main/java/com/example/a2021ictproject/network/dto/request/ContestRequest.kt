/**
 * 대회 생성 시 사용하는 request class
 * */
package com.example.a2021ictproject.network.dto.request

import com.example.a2021ictproject.network.dto.response.Prize
import java.sql.Timestamp

data class ContestRequest(
    val title: String,
    val content: String,
    val startdate: String,
    val duedate: String,
    val prize: List<Prize>
)