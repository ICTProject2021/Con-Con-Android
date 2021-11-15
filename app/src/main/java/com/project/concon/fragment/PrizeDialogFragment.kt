package com.project.concon.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.project.concon.databinding.FragmentPrizeDialogBinding
import com.project.concon.network.dto.response.Prize
import com.project.concon.utils.MessageUtils
import com.project.concon.viewmodel.PrizeDialogViewModel

class PrizeDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentPrizeDialogBinding
    private val viewModelPrize : PrizeDialogViewModel by activityViewModels()

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
                if (binding.price.text.length >= 8) {
                    MessageUtils.showDefaultDialog(requireActivity(), "대회 상금의 최대 금액은 1억원입니다.")
                } else {
                    viewModelPrize.addPrizeList(
                        Prize(
                            viewModelPrize.getLastRank() + 1,
                            Integer.parseInt(binding.price.text.trim().toString())
                        )
                    )
                }

                dismiss()
            }
        }
    }
}