package com.example.a2021ictproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a2021ictproject.R
import com.example.a2021ictproject.network.dto.response.Contest

//val contest: List<Contest>

class MainRecyclerViewAdapter() : RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder>(){

    private var dataList = mutableListOf<Contest>()
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener?) {
        if (listener != null) {
            this.mListener = listener
        }
    }

    fun setData(data: List<Contest>) {
        this.dataList.clear()
        this.dataList.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.item_contest_title)
        val dueLine: TextView = view.findViewById(R.id.item_Deadline)
        val profile: ImageView = view.findViewById(R.id.item_profile)
        val user: TextView = view.findViewById(R.id.item_user)

        fun bind(data: Contest) {
            title.text = data.title
            dueLine.text = data.dueDate.toString()
            user.text = data.host
            Glide.with(profile)
                .load(data.profilepicture)
                .into(profile)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_main, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(dataList[position])
        viewHolder.itemView.setOnClickListener {
            mListener.onClick(it, position)
        }
    }

    override fun getItemCount() = dataList.size

}
