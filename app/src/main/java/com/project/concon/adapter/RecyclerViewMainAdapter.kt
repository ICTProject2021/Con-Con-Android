package com.project.concon.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.concon.R
import com.project.concon.bind.setImage
import com.project.concon.network.dto.response.Contest
import java.text.SimpleDateFormat
import java.util.*

class RecyclerViewMainAdapter : RecyclerView.Adapter<RecyclerViewMainAdapter.ViewHolder>(){

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
            dueLine.text = data.duedate.toString()
            user.text = data.host
            dueLine.text = getDateText(data.duedate)

            if (data.profile != null)
                profile.setImage(data.profile)
        }

        private fun getDateText(dueDate: String?): String {
            Log.d("getDateText", dueDate.toString())
            return "마감 " + if (dueDate != null) {
                val today = Date(System.currentTimeMillis())
                val lastDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", Locale.getDefault()).parse(dueDate)

                val dif = (lastDate.time - today.time) / (24*60*60*1000)

                dif.toString() + "일 전"
            } else {
                ""
            }
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
            mListener.onClick(dataList[position].ID!!)
        }
    }

    override fun getItemCount() = dataList.size

}
