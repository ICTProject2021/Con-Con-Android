package com.project.concon.widget.extension

import android.app.Activity
import android.content.Intent

fun Activity.startActivity(activity: Class<*>) {
    startActivity(Intent(this, activity).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
}

fun Activity.startActivityWithFinish(activity: Class<*>) {
    startActivity(Intent(this, activity).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    this.finish()
}