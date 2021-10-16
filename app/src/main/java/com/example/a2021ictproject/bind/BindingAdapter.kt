package com.example.a2021ictproject.bind

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.example.a2021ictproject.adapter.JoinContestRecyclerViewAdapter
import com.example.a2021ictproject.network.dto.response.Participant

interface BindingAdapter {

    // 대회 참여 recyclerView
    @BindingAdapter("submitList")
    fun RecyclerView.submitList(list: ObservableArrayList<Participant>) {
        val adapter = JoinContestRecyclerViewAdapter()
        adapter.setList(list)
    }

    @BindingAdapter("imageUrl")
    fun ImageView.loadImage(url: String) {

    }
}