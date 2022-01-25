package com.project.concon.widget.recyclerview.adapter

import android.content.ContentResolver
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.concon.databinding.ItemImageBinding
import com.project.concon.widget.utils.ImagePicker

class RecyclerViewImageAdapter: RecyclerView.Adapter<RecyclerViewImageAdapter.ViewHolder>() {

    private val list = arrayListOf<Uri>()

    private lateinit var contentResolver: ContentResolver

    inner class ViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(uri: Uri, position: Int) {
            binding.uri = uri

            binding.imageView.setImageBitmap(ImagePicker.getBitmap(contentResolver, uri))

            binding.layoutRemove.setOnClickListener {
                list.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        contentResolver = parent.context.contentResolver

        val inflated = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(inflated)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    fun setList(list: List<Uri>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}