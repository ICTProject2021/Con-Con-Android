package com.project.concon.widget.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.concon.databinding.ItemPrizeBinding
import com.project.concon.model.remote.dto.response.Prize

class RecyclerViewPrizeAdapter : RecyclerView.Adapter<RecyclerViewPrizeAdapter.ViewHolder>() {

    private lateinit var setOnItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onClick(pos: Int)
    }

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        setOnItemClickListener = object : OnItemClickListener {
            override fun onClick(pos: Int) {
                listener(pos)
            }
        }
    }

    private val list = arrayListOf<Prize>()

    inner class ViewHolder(private val binding: ItemPrizeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(prize: Prize, pos: Int) {
            binding.tvPrice.text = prize.price.toString()
            binding.tvRank.text = prize.rank.toString()
            binding.btnClose.setOnClickListener {
                setOnItemClickListener.onClick(pos)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPrizeBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    fun setList(list: List<Prize>) {
        this.list.clear()
        this.list.addAll(list)
        notifyItemChanged(list.size-1)
    }
}