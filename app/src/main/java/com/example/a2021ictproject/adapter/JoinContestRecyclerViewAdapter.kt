package com.example.a2021ictproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a2021ictproject.databinding.RvItemJoinContestBinding
import com.example.a2021ictproject.network.dto.response.Participant

class JoinContestRecyclerViewAdapter : RecyclerView.Adapter<JoinContestRecyclerViewAdapter.ViewHolder>() {

    private val list: MutableList<Participant> = mutableListOf()

    private lateinit var binding: RvItemJoinContestBinding

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RvItemJoinContestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        binding.participant = list[position]
    }

    override fun getItemCount(): Int = list.size

    fun setList(list: List<Participant>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}
