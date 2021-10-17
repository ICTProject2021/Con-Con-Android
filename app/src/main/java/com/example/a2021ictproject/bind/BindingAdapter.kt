package com.example.a2021ictproject.bind

import android.view.View
import android.view.View.*
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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

// 대회 참여 recyclerView
@BindingAdapter("submitList")
fun RecyclerView.submitList(list: ObservableArrayList<Participant>) {
    val adapter = JoinContestRecyclerViewAdapter()
    adapter.setList(list)
}

