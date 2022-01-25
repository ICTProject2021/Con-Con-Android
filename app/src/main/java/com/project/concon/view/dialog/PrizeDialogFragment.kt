package com.project.concon.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.project.concon.databinding.DialogFragmentPrizeBinding
import com.project.concon.model.remote.dto.response.Prize
import com.project.concon.viewmodel.PrizeViewModel
import com.project.concon.widget.utils.MessageUtils
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PrizeDialogFragment : DialogFragment() {

    private lateinit var binding: DialogFragmentPrizeBinding
    private val prizeViewModel : PrizeViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentPrizeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addButton.setOnClickListener {
            if (binding.price.text.isEmpty()) {
                MessageUtils.showToast(requireContext(), "상금을 입력해주세요.")
            } else {
                if (binding.price.text.length >= 8) {
                    MessageUtils.showDefaultDialog(requireActivity(), "대회 상금의 최대 금액은 1억원입니다.")
                } else {
                    addPrize(getLastRank()+1, Integer.parseInt(binding.price.text.trim().toString()))
                    dismiss()
                }
            }
        }
    }

    private fun getLastRank(): Int =
        with(prizeViewModel.prizeList.value!!) {
            if (this.isNotEmpty()) this.size-1
            else 0
        }

    private fun addPrize(rank: Int, price: Int) {
        prizeViewModel.apply {
            val prize = Prize(rank, price)
            val list = mutableListOf<Prize>()

            list.addAll(prizeList.value?: listOf())
            list.add(prize)

            prizeList.value = list
        }
    }
}