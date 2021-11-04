package com.project.concon.adapter

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.concon.databinding.RvItemJoinContestImageBinding

class RecyclerViewJoinContestImageAdapter: RecyclerView.Adapter<RecyclerViewJoinContestImageAdapter.ViewHolder>() {

    private val list: MutableList<Uri> = mutableListOf()

    private lateinit var binding: RvItemJoinContestImageBinding
    private lateinit var context: Context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RvItemJoinContestImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ViewHolder(
            binding.root
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        binding.uri = list[position]
        val uri = list[position]

        Log.d("imageAdapter-onBindViewHolder", uri.toString())
        val contentResolver: ContentResolver = context.contentResolver

        val bitmap: Bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(contentResolver, uri)
        }

        binding.imageView.setImageBitmap(bitmap)
    }

    override fun getItemCount(): Int = list.size

    fun setList(list: List<Uri>) {
        Log.d("imageAdapter", list.toString())

        this.list.clear()
        this.list.addAll(list)
        this.notifyDataSetChanged()
    }
}