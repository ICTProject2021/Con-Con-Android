package com.project.concon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.concon.R
import com.project.concon.network.dto.response.Winner

class RecyclerViewWinnerAdapter: RecyclerView.Adapter<RecyclerViewWinnerAdapter.ViewHolder>() {
    private var dataList = mutableListOf<Winner>()

    fun setData(data: List<Winner>) {
        this.dataList.clear()
        this.dataList.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val rank: TextView = view.findViewById(R.id.item_rank)
        val nickName: TextView = view.findViewById(R.id.item_nickName)
        val price: TextView = view.findViewById(R.id.item_price)

        fun bind(data: Winner) {
            rank.text = data.rank.toString() + "등"
            nickName.text = data.nickname
            price.text = data.price.toString() + "원"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_winner, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount() = dataList.size
}