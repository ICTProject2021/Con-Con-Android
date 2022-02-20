package com.project.concon.model.remote.dto.response

data class ParticipatedContest(
    val ID: Int?,
    val title: String?,
    val content: String?,
    val duedate: String?,
    val profilepicture: String?,
    val host: String?,
    val prize: List<Prize>?
)
