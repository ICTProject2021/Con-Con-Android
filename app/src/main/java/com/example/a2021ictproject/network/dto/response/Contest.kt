package com.example.a2021ictproject.network.dto.response

data class ContestList(val items: ArrayList<Contest>)
data class Contest(
    val ID: Int,
    val title: String,
    val dueDate: Long,
    val profilepicture: String,
    val host: String
)