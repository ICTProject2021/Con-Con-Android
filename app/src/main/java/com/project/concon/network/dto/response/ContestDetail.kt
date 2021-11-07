package com.project.concon.network.dto.response

data class ContestDetail(
    val id: Int,
    val title: String,
    val content: String,
    val startdate: String,
    val duedate: String,
    val host: String,
    val isHost: Boolean,
    val prize: List<Prize>
)
