package com.example.a2021ictproject.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.a2021ictproject.R
import com.example.a2021ictproject.databinding.FragmentPrizeBinding
import com.example.a2021ictproject.network.dto.response.Prize
import com.example.a2021ictproject.viewmodel.DialogViewModel

class PrizeFragment : Fragment() {

    private lateinit var binding: FragmentPrizeBinding
    private val viewModel: DialogViewModel by activityViewModels()
    private var rank: Int = 1
    private var prize: List<Prize> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPrizeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addPrizeButton.setOnClickListener {
            PrizeDialogFragment().show(
                parentFragmentManager, "PrizeDialog"
            )
        }
//        val prize = viewModel.getPrize()
        viewModel.prize.observe(viewLifecycleOwner, Observer {
            Log.d("prize", it.toString())
            addView(rank, it)
            rank++
        })
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