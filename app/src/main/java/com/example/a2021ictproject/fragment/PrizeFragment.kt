package com.example.a2021ictproject.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.a2021ictproject.R
import com.example.a2021ictproject.databinding.FragmentPrizeBinding
import com.example.a2021ictproject.network.dto.response.Prize
import com.example.a2021ictproject.viewmodel.DialogViewModel

class PrizeFragment : Fragment() {

    private lateinit var binding: FragmentPrizeBinding
    private val viewModel: DialogViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrizeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        for (i in 0 until viewModel.prizeList.value!!.size - 2) {
//            val it = viewModel.prizeList.value!![i]
//            addView(it.rank, it.price)
//        }
//        viewModel.prizeList.value?.forEach {
//            addView(it.rank, it.price)
//        }

        binding.addPrizeButton.setOnClickListener {
            PrizeDialogFragment().show(
                parentFragmentManager, "PrizeDialog"
            )
        }

        viewModel.prizeList.observe(viewLifecycleOwner) {
            binding.prizeLayout.removeAllViews()

            it.forEach { prize ->
                addView(prize.rank, prize.price)
            }
//            if (it.isNotEmpty()) {
//                val prize = it[it.size-1]
//                addView(prize.rank, prize.price)
//            }
        }

        binding.btnBackPrize.setOnClickListener {
            findNavController().navigate(PrizeFragmentDirections.actionPrizeFragmentToCreateContestFragment())
        }
    }

    private fun addView(rank: Int, prize: Int) {
        val view = layoutInflater.inflate(R.layout.item_prize, null, false )

        val rankTextView: TextView = view.findViewById(R.id.rankTextView)
        rankTextView.text = rank.toString() + "등"

        val priceTextView: TextView = view.findViewById(R.id.priceTextView)
        priceTextView.text = prize.toString()

        binding.prizeLayout.addView(view)
    }

}