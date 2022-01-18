package com.project.concon.widget.recyclerview.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.concon.databinding.ItemJoinContestImageBinding
import com.project.concon.widget.utils.ImagePicker

class RecyclerViewJoinContestImageAdapter: RecyclerView.Adapter<RecyclerViewJoinContestImageAdapter.ViewHolder>() {

    private val list: MutableList<Uri> = mutableListOf()

    private lateinit var binding: ItemJoinContestImageBinding
    private lateinit var context: Context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemJoinContestImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ViewHolder(
            binding.root
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        binding.uri = list[position]
        val uri = list[position]

        Log.d("imageAdapter-onBindViewHolder", uri.toString())
        binding.imageView.setImageBitmap(
            ImagePicker.getBitmap(context.contentResolver, uri)
        )
    }

    override fun getItemCount(): Int = list.size

    fun setList(list: List<Uri>) {
        Log.d("imageAdapter", list.toString())

        this.list.clear()
        this.list.addAll(list)
        this.notifyDataSetChanged()
    }
}