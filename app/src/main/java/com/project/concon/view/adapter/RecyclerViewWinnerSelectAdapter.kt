package com.project.concon.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.concon.R
import com.project.concon.model.remote.dto.response.Participant
import com.project.concon.view.bind.setImage

class RecyclerViewWinnerSelectAdapter : RecyclerView.Adapter<RecyclerViewWinnerSelectAdapter.ViewHolder>() {

    private lateinit var setOnItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onClick(v: View, participant: Participant)
    }

    fun setOnItemClickListener(listener: (View, Participant) -> Unit) {
        setOnItemClickListener = object : OnItemClickListener {
            override fun onClick(v: View, participant: Participant) {
                listener(v, participant)
            }
        }
    }

    private val dataList = mutableListOf<Participant>()

    fun setData(dataList: List<Participant>) {
        this.dataList.clear()
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nickname: TextView = view.findViewById(R.id.item_nickName_select)
        val profile: ImageView = view.findViewById(R.id.item_profile_select)

        fun bind(data: Participant) {
            nickname.text = data.nickname

            if (data.profile != null) {
                profile.setImage(data.profile)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_winner_select, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
        holder.itemView.setOnClickListener {
            setOnItemClickListener.onClick(it, dataList[position])
        }
    }

    override fun getItemCount(): Int = dataList.size
}