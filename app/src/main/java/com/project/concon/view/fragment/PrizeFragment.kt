package com.project.concon.view.fragment

import com.project.concon.base.BaseFragment
import com.project.concon.databinding.FragmentPrizeBinding
import com.project.concon.view.dialog.PrizeDialogFragment
import com.project.concon.viewmodel.CreateContestViewModel
import com.project.concon.viewmodel.PrizeViewModel
import com.project.concon.widget.recyclerview.adapter.RecyclerViewPrizeAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.text.NumberFormat
import java.util.*

class PrizeFragment : BaseFragment<FragmentPrizeBinding, PrizeViewModel>() {

    override val viewModel: PrizeViewModel by sharedViewModel()
    private val createContestViewModel: CreateContestViewModel by sharedViewModel()

    private val adapter = RecyclerViewPrizeAdapter()

    override fun init() {
        binding.rvPrize.adapter = adapter

        adapter.setOnItemClickListener {
            removePrize(it)
        }

        binding.btnBack.setOnClickListener {
            createContestViewModel.prize.value = "총 ${getTotalPrize()}원 "
            navController.popBackStack()
        }

        binding.fabAdd.setOnClickListener {
            PrizeDialogFragment().show(parentFragmentManager, "PrizeDialog")
        }
    }

    override fun observerViewModel() {
        viewModel.prizeList.observe(viewLifecycleOwner) {
            adapter.setList(it)
        }
    }

    private fun getTotalPrize(): String {
        var result = 0
        viewModel.prizeList.value?.forEach {
            result += it.price
        }

        val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault()) // 돈 자릿수따라 콤마 처
        return numberFormat.format(result)
    }

    private fun removePrize(index: Int) {
        viewModel.prizeList.apply {
            value = value!!.filter { it != value!![index] }
        }
    }
}