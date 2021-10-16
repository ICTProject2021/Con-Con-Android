package com.example.a2021ictproject.bind

import android.view.View
import android.view.View.*
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("setVisible")
fun View.setVisible(isVisible: Boolean) {
    visibility = if (isVisible) {
        VISIBLE
    } else INVISIBLE
}

@BindingAdapter("setError")
fun TextInputLayout.setError(errorMsg: String?) {
    this.error = errorMsg
}

