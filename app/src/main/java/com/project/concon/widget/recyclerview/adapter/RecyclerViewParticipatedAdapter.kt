package com.project.concon.widget.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.concon.R
import com.project.concon.model.remote.dto.response.Contest
import com.project.concon.widget.bind.setImage
import java.text.SimpleDateFormat
import java.util.*

class RecyclerViewParticipatedAdapter :
    RecyclerView.Adapter<RecyclerViewParticipatedAdapter.ViewHolder>() {

    private var dataList = mutableListOf<Contest>()
    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onClick(id: Int)
    }

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        mListener = object : OnItemClickListener {
            override fun onClick(id: Int) {
                listener(id)
            }
        }
    }

    fun setList(list: List<Contest>) {
        this.dataList.clear()
        this.dataList.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.item_contest_title)
        val dueLine: TextView = view.findViewById(R.id.item_Deadline)
        val profile: ImageView = view.findViewById(R.id.item_profile)
        val user: TextView = view.findViewById(R.id.item_user)

        fun bind(data: Contest) {
            title.text = data.title
            dueLine.text = getDataText(data.duedate)
            user.text = data.host

            if (data.profile != null) {
                profile.setImage(data.profile)
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
            mListener.onClick(dataList[position].ID!!)
        }
    }

    override fun getItemCount() = dataList.size
}