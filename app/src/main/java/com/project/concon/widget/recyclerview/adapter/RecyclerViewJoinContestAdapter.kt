package com.project.concon.widget.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.concon.databinding.ItemJoinContestBinding
import com.project.concon.model.remote.dto.response.Participant

class RecyclerViewJoinContestAdapter : RecyclerView.Adapter<RecyclerViewJoinContestAdapter.ViewHolder>() {

    private lateinit var setOnClickJoinListener: OnClickJoinListener

    interface OnClickJoinListener {
        fun onClick(partId: Int)
    }

    fun onClickJoinListener(listener: (Int) -> Unit) {
        setOnClickJoinListener = object : OnClickJoinListener {
            override fun onClick(partId: Int) {
                listener(partId)
            }
        }
    }

    private val list: MutableList<Participant> = mutableListOf()

    private lateinit var binding: ItemJoinContestBinding

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemJoinContestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        binding.participant = data

        binding.btnFavorites.setOnClickListener {
            setOnClickJoinListener.onClick(data.ID)
        }
    }

    override fun getItemCount(): Int = list.size

    fun setList(list: List<Participant>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun setLikes() {
        // 좋아요 눌렀을 때 ui 변경~~
    }
}
