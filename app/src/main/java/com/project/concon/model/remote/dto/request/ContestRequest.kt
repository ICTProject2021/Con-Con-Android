/**
 * 대회 생성 시 사용하는 request class
 * */
package com.project.concon.model.remote.dto.request

import com.project.concon.model.remote.dto.response.Prize

data class ContestRequest(
    val title: String,
    val content: String,
    val startdate: String,
    val duedate: String,
    val prize: List<Prize>
)