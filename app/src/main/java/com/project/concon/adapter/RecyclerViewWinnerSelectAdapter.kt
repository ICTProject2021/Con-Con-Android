package com.project.concon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.concon.R
import com.project.concon.network.dto.response.Participant
import com.project.concon.network.dto.response.ParticipatedContest

class RecyclerViewWinnerSelectAdapter : RecyclerView.Adapter<RecyclerViewWinnerSelectAdapter.ViewHolder>() {

    private val dataList = mutableListOf<Participant>()
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener?) {
        if (listener != null) {
            this.mListener = listener
        }
    }

    fun setData(dataList: List<Participant>) {
        this.dataList.clear()
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }

    fun getData(position: Int) : Participant{
        return dataList[position]
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nickname: TextView = view.findViewById(R.id.item_nickName)
        val profile: ImageView = view.findViewById(R.id.item_profile)

        fun bind(data: Participant) {
            nickname.text = data.nickname

            if (data.profile != null) {
                Glide.with(profile)
                    .load(data.profile)
                    .into(profile)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_winner_select, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
        holder.itemView.setOnClickListener {
            mListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int = dataList.size
}