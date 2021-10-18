package com.example.a2021ictproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a2021ictproject.databinding.RvItemJoinContestImageBinding

class JoinContestImageRecyclerViewAdapter: RecyclerView.Adapter<JoinContestImageRecyclerViewAdapter.ViewHolder>() {

    private val list: MutableList<String> = mutableListOf()

    private lateinit var binding: RvItemJoinContestImageBinding

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RvItemJoinContestImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        binding.url = list[position]
    }

    override fun getItemCount(): Int = list.size

    fun setList(list: List<String>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}