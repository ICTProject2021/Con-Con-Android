package com.example.a2021ictproject.bind

import android.graphics.LinearGradient
import android.graphics.Shader
import android.view.View
import android.view.View.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a2021ictproject.R
import com.example.a2021ictproject.adapter.JoinContestRecyclerViewAdapter
import com.example.a2021ictproject.network.dto.response.Participant
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("setVisible")
fun View.setVisible(isVisible: Boolean) {
    visibility = if (isVisible) {
        VISIBLE
    } else INVISIBLE
}

// TextInputLayout 에러 메세지를 변경함
@BindingAdapter("setError")
fun TextInputLayout.setError(errorMsg: String?) {
    this.error = errorMsg
}

@BindingAdapter("loadUrl")
fun ImageView.setImage(url: String) {
    Glide.with(context).load(url).into(this)
}

@BindingAdapter("submitList")
fun RecyclerView.submitList(list: List<Participant>) {
    val adapter = JoinContestRecyclerViewAdapter()
    adapter.setList(list)
}

// 텍스트뷰 그라데이션 적용
@BindingAdapter("setShader")
fun TextView.setShader(b: Boolean) {
    val startColor = ContextCompat.getColor(context, R.color.main_gradient_start_color)
    val endColor = ContextCompat.getColor(context, R.color.main_gradient_end_color)

    val shader = LinearGradient(
        0f,
        0f,
        this.paint.measureText(this.text.toString()),
        this.textSize,
        intArrayOf(startColor, endColor),
        floatArrayOf(0f, 0.6f),
        Shader.TileMode.CLAMP
    )

    this.paint.shader = shader
}
