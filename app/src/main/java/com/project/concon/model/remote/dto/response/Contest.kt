package com.project.concon.model.remote.dto.response

data class Contest(
    val ID: Int?,
    val title: String?,
    val content: String?,
    val startdate: String?,
    val duedate: String?,
    val host: String?,
    val isHost: Boolean,
    val profile: String?
)