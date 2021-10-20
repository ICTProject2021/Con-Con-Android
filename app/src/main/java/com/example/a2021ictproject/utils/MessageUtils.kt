package com.example.a2021ictproject.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.a2021ictproject.R

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