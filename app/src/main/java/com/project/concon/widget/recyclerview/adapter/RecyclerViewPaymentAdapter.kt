package com.project.concon.widget.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.concon.databinding.ItemPaymentBinding
import com.project.concon.widget.data.Payment

class RecyclerViewPaymentAdapter(private val list: List<Payment>) : RecyclerView.Adapter<RecyclerViewPaymentAdapter.ViewHolder>() {

    private lateinit var setOnClickPaymentListener: OnClickPaymentListener

    interface OnClickPaymentListener {
        fun onClick(pos: Int)
    }

    fun setOnClickPaymentListener(listener: (Int) -> Unit) {
        setOnClickPaymentListener = object : OnClickPaymentListener {
            override fun onClick(pos: Int) {
                listener(pos)
            }
        }
    }

    private lateinit var binding: ItemPaymentBinding

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemPaymentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        binding.data = list[position]

        binding.cvPayment.setOnClickListener {
            setOnClickPaymentListener.onClick(position)
        }
    }

    override fun getItemCount(): Int = list.size
}