package com.project.concon.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.project.concon.databinding.FragmentPrizeDialogBinding
import com.project.concon.network.dto.response.Prize
import com.project.concon.viewmodel.DialogViewModel

class PrizeDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentPrizeDialogBinding
    private val viewModel : DialogViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrizeDialogBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addButton.setOnClickListener {
            if (binding.price.text.isEmpty()) {
                TODO()
            } else {
                Log.d("prize", binding.price.text.toString())
                viewModel.addPrizeList(
                    Prize(
                    viewModel.getLastRank() + 1,
                    Integer.parseInt(binding.price.text.trim().toString())
                )
                )
                dismiss()
            }
        }
    }
}