package com.project.concon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.concon.R
import com.project.concon.network.dto.response.ParticipatedContest
import java.text.SimpleDateFormat
import java.util.*

class ParticipatedRecyclerViewAdapter :
    RecyclerView.Adapter<ParticipatedRecyclerViewAdapter.ViewHolder>() {

    private var dataList = mutableListOf<ParticipatedContest>()
    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        if (listener != null) {
            this.mListener = listener
        }
    }

    fun setData(data: List<ParticipatedContest>) {
        this.dataList.clear()
        this.dataList.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.item_contest_title)
        val dueLine: TextView = view.findViewById(R.id.item_Deadline)
        val profile: ImageView = view.findViewById(R.id.item_profile)
        val user: TextView = view.findViewById(R.id.item_user)

        fun bind(data: ParticipatedContest) {
            title.text = data.title
            dueLine.text = getDataText(data.duedate)
            user.text = data.host

            if (data.profilepicture != null) {
                Glide.with(profile)
                    .load(data.profilepicture)
                    .into(profile)
            }
        }

        private fun getDataText(dueDate: String?): String {
            return "마감" + if (dueDate != null) {
                val today = Date(System.currentTimeMillis())
                val lastDate =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", Locale.getDefault()).parse(
                        dueDate
                    )

                val dif = (lastDate.time - today.time) / (24 * 60 * 60 * 1000)

                dif.toString() + "일 전"
            } else {
                " "
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
        holder.itemView.setOnClickListener {
            mListener.onClick(it, position)
        }
    }

    override fun getItemCount() = dataList.size
}