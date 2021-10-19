package com.example.a2021ictproject.utils

import android.app.AlertDialog
import android.content.Context

object MessageUtils {
    fun showDialog(context: Context, title: String, msg: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)

        builder.setTitle(title)
            .setMessage(msg)
            .setPositiveButton("확인") { _, _ -> }
            .setCancelable(false)
            .create()
    }
}