package com.project.concon.widget.utils

// 리스트의 모든 요소의 공백이 없는지 검사
fun List<String?>.isNotBlankAll(): Boolean {
    this.forEach { item ->
        if (item.isNullOrBlank()) {
            return false
        }
    }

    return true
}

// 리스트의 요소 중 공백이 하나라도 있는지 검사
fun List<String>.isBlankItem(): Boolean {
    this.forEach { item ->
        if (item.isNullOrBlank()) {
            return true
        }
    }

    return false
}