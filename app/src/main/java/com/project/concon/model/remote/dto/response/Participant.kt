package com.project.concon.model.remote.dto.response

data class Participant(
    val ID: Int,
    val nickname: String,
    val profile: String?,
    val content: String,
    val likes: Int,
    val URL: List<String>?
)