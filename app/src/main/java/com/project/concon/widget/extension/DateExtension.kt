package com.project.concon.widget.extension

import java.text.SimpleDateFormat
import java.util.*

const val DATE = 0
const val SECOND = 1

fun Long.getDateAsString(type: Int): String {
    val pattern = when (type) {
        DATE -> "yyyy-MM-dd"
        SECOND -> "yyyy-MM-dd HH:mm:ss.sss"
        else -> ""
    }

    return SimpleDateFormat(pattern).format(this)
}

fun Date.formatToString(): String =
    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(this)