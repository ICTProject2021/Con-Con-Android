package com.project.concon.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.project.concon.R
import com.project.concon.databinding.FragmentPrizeBinding
import com.project.concon.viewmodel.CreateContestViewModel
import com.project.concon.viewmodel.PrizeDialogViewModel
import java.text.NumberFormat
import java.util.*

class PrizeFragment : Fragment() {

    private lateinit var binding: FragmentPrizeBinding
    private val viewModelPrize: PrizeDialogViewModel by activityViewModels()
    private val cViewModel: CreateContestViewModel by activityViewModels()

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

        viewModelPrize.prizeList.observe(viewLifecycleOwner) {
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
            cViewModel.prize.value = "총 ${totalPrice()}원 "
            findNavController().navigate(PrizeFragmentDirections.actionPrizeFragmentToCreateContestFragment())
        }
    }

    private fun addView(rank: Int, prize: Int) {
        val view = layoutInflater.inflate(R.layout.item_prize, null, false )

        val rankTextView: TextView = view.findViewById(R.id.rankTextView)
        rankTextView.text = rank.toString() + "등"

        val priceTextView: TextView = view.findViewById(R.id.priceTextView)

        val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
        val cash = numberFormat.format(prize)

        priceTextView.text = cash + "원"

        binding.prizeLayout.addView(view)
    }

    private fun totalPrice() : Int {
        var result: Int = 0
        viewModelPrize.prizeList.value?.forEach {
            result += it.price
        }
        return result
    }
}