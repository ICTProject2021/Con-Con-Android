package com.example.a2021ictproject.utils

// 리스트의 모든 요소의 공백 검사
fun List<String?>.isNotBlankAll(): Boolean {
    this.forEach { item ->
        if (item.isNullOrBlank()) {
            return false
        }
    }

    return true
}