package com.project.concon.utils

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast

object MessageUtils {
    fun showDialog(context: Context, title: String, msg: String) =
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(msg)
            .setPositiveButton("확인") { _, _ -> }
            .setCancelable(false)
            .create()
            .show()

    fun showToast(context: Context, msg: String) =
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}