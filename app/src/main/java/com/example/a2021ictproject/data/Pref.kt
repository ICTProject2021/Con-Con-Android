package com.example.a2021ictproject.data

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Pref(context: Context) {
    private val pref = context.getSharedPreferences("pref", MODE_PRIVATE)

}