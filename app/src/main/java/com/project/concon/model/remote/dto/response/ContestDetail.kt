package com.project.concon.model.remote.dto.response

data class ContestDetail(
    val ID: Int,
    val title: String,
    val content: String,
    val startdate: String,
    val duedate: String,
    val host: String,
    val profile: String,
    val isHost: Boolean,
    val prize: List<Prize>
)
