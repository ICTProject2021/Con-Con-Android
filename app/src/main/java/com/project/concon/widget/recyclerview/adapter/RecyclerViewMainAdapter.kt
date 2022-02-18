package com.project.concon.widget.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.concon.databinding.ItemHomeBinding
import com.project.concon.databinding.ItemLoadingBinding
import com.project.concon.model.remote.dto.response.Contest
import com.project.concon.widget.bind.setImage
import java.text.SimpleDateFormat

class RecyclerViewMainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = -1
    }

    private var list = arrayListOf<Contest>()
    private lateinit var onItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onClick(id: Int)
    }

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = object : OnItemClickListener {
            override fun onClick(id: Int) {
                listener(id)
            }
        }
    }

    inner class BoardViewHolder(private val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Contest) {
            binding.apply {
                tvTitle.text = data.title
                tvUser.text = data.host
                tvDeadline.text = getDeadLineText(data.duedate)
                ivProfile.setImage(data.profile!!)

                itemView.setOnClickListener {
                    onItemClickListener.onClick(data.ID)
                }
            }
        }

        private fun getDeadLineText(dueDate: String?): String {
            val dateFormat = SimpleDateFormat("yyyyMMdd")
            val today = dateFormat.parse(System.currentTimeMillis().toString()).time
            val lastDate = dateFormat.parse(dueDate).time

            return "마감 ${(lastDate-today) / (24*60*60*1000)}일 전"
        }
    }

    inner class LoadingViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_TYPE_ITEM -> BoardViewHolder(ItemHomeBinding.inflate(LayoutInflater.from(viewGroup.context)))
            else -> LoadingViewHolder(ItemLoadingBinding.inflate(LayoutInflater.from(viewGroup.context)))
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BoardViewHolder) holder.bind(list[position])
        else (holder is LoadingViewHolder)
    }

    override fun getItemViewType(position: Int): Int =
        if (null != list[position].content) VIEW_TYPE_ITEM
        else VIEW_TYPE_LOADING

    override fun getItemCount() = list.size

    fun setList(list: List<Contest>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}
